package org.fms.web.controller.system;

import org.fms.mysql.entity.User;
import org.fms.mysql.model.UserQo;
import org.fms.mysql.repository.UserRepository;
import org.fms.web.utils.ContentResults;
import org.fms.web.utils.Results;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by lion on 2017/8/8.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public ContentResults<List<User>>findAll2(){
        List<User> userList= userRepository.findAll();
        userList.forEach(user -> {
            System.out.println(user);
        });
        return new ContentResults<List<User>>(200,"Find Users",userList);
    }

    @RequestMapping(path = "/findPage",method = RequestMethod.GET)
    @ResponseBody
    public ContentResults<Map> findAll(UserQo userQo){
        System.out.println(userQo.getPage());
        try {
            Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.ASC, "id"));
            Page<User> userPage = userRepository.findAll(pageable);
            Map<String,Page> map= new HashMap<String,Page>();
            map.put("data",userPage);
            return new ContentResults<Map>(200,"Find Users",map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public ContentResults<User> add(User user){
        User user1 = userRepository.save(user);
        BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
        user.setPassword(bpe.encode(user.getPassword()));
        return new ContentResults<User>(200,"Add user success",user1);
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.DELETE)
    public Results delete(@PathVariable Long id){
        userRepository.delete(id);
        return new Results(200,"Delete user success");
    }

    @RequestMapping(path = "/edit",method = RequestMethod.PUT)
    public ContentResults<User> updata(User user){
        User user1 = userRepository.save(user);
        return new ContentResults<User>(200,"Edit user success",user1);
    }

    @RequestMapping(path = "/find/{id}",method = RequestMethod.GET)
    public ContentResults<User> find(@PathVariable Long id){
        User user = userRepository.findOne(id);
        if(user==null){
            return new ContentResults<User>(404,"Can't find the user",null);
        }
        return new ContentResults<User>(200,"Find the User",user);
    }
}
