package org.fms.web.controller.system;

import org.fms.mysql.entity.User;
import org.fms.mysql.repository.UserRepository;
import org.fms.web.service.ImageCode;
import org.fms.web.utils.results.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

@RestController
public class LoginController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/images/imagecode")
	public String imagecode(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OutputStream os = response.getOutputStream();
		Map<String,Object> map = ImageCode.getImageCode(60, 20, os);

		String simpleCaptcha = "simpleCaptcha";
		request.getSession().setAttribute(simpleCaptcha, map.get("strEnsure").toString().toLowerCase());
		request.getSession().setAttribute("codeTime",new Date().getTime());
		try {
			ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
		} catch (IOException e) {
			return "";
		}
		return null;
	}

	@RequestMapping(value = "/loginSuccess",method = RequestMethod.GET)
	public Results success(HttpServletRequest request, Authentication authentication){
		User userDetails = (User)authentication.getPrincipal();
		String Ip = getIpAddress(request);
		logger.info("登录用户user:" + userDetails.getName() + " login"+request.getContextPath());
		logger.info("IP:" + Ip);
		userDetails.setIP(Ip);
		return new Results(200,"Login Success");
	}

	@RequestMapping(value = "/failure",method = RequestMethod.GET)
	public Results failure(){
		return new Results(401,"Login failure");
	}

	@RequestMapping(value = "/test",method = RequestMethod.GET)
	public Results test(){
		return new Results(201,"test");
	}

	@RequestMapping(value = "/checkcode",method = RequestMethod.GET)
	public String checkcode(HttpServletRequest request, HttpSession session)
			throws Exception {
        String checkCode = request.getParameter("checkCode");
		Object cko = session.getAttribute("simpleCaptcha") ; //验证码对象
		if(cko == null){
            request.setAttribute("errorMsg", "验证码已失效，请重新输入！验证码为空");
            return "验证码已失效，请重新输入！验证码为空";
        }

        String captcha = cko.toString();
        Date now = new Date();
        Long codeTime = Long.valueOf(session.getAttribute("codeTime")+"");
        if(StringUtils.isEmpty(checkCode) || captcha == null ||  !(checkCode.equalsIgnoreCase(captcha))){
            request.setAttribute("errorMsg", "验证码错误！");
            return "验证码错误！";
        }else if ((now.getTime()-codeTime)/1000/60>5){//验证码有效时长为5分钟
            request.setAttribute("errorMsg", "验证码已失效，请重新输入！"+(now.getTime()-codeTime)/1000/60);
            return "验证码已失效，请重新输入！"+(now.getTime()-codeTime)/1000/60;
        }else {
            session.removeAttribute("simpleCaptcha");
            return "1";
        }

	}
	/*
	*获得登陆时的ip地址
	*/
	public String getIpAddress(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
