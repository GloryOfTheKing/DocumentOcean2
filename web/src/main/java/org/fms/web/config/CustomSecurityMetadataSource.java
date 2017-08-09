package org.fms.web.config;

import org.apache.log4j.Logger;
import org.fms.mysql.entity.Permission;
import org.fms.mysql.entity.Role;
import org.fms.mysql.repository.PermissionRepository;
import org.fms.mysql.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;
import java.util.function.Consumer;

/*
*@Author 郭恒
*@Date 2017/7/5 16:37
*调用安全元数据源的过滤器
*/
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{
    private static final Logger logger = Logger.getLogger(CustomSecurityMetadataSource .class);

    private PermissionRepository permissionRepository;
    private Map<String, Collection<ConfigAttribute>> resourceMap = null;
    private PathMatcher pathMatcher = new AntPathMatcher();//URLs字符串匹配

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    @Autowired
    public CustomSecurityMetadataSource  (PermissionRepository permissionRepository) {
        super();
        this.permissionRepository = permissionRepository;
        resourceMap = loadResourceMatchAuthority();
    }
    /*
    *  加载用户角色和用户权限
    */

    private Map<String, Collection<ConfigAttribute>> loadResourceMatchAuthority() {
        Map<String, Collection<ConfigAttribute>> map = new HashMap<String, Collection<ConfigAttribute>>();
        List<Permission> permissions = permissionRepository.findAll();
        if(permissions != null && permissions.size()!=0){
            for(Permission permission : permissions){
                logger.info("Permission --------------> "+permission.getName());
                Collection<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
                Set<Role> roles= permission.getRoles();
                roles.stream().forEach(role -> {
                    logger.info("Role --------------> "+role.getName());
                    ConfigAttribute config = new SecurityConfig(role.getName());
                    list.add(config);
                });
                //key：url, value：roles
                map.put(permission.getUrl(), list);
            }
        }else{
            logger.error("oles must be set");
        }

        logger.info("Loaded Roles Resources.");
        return map;

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();

        logger.debug("request url is  " + url);
       if(resourceMap == null)
            resourceMap = loadResourceMatchAuthority();

        Iterator<String> ite = resourceMap.keySet().iterator();
/*        while(ite.hasNext()){
            String role = ite.next();
            Collection<ConfigAttribute> collection = resourceMap.get(role);
            Iterator<ConfigAttribute> iterable = collection.iterator();
            while (iterable.hasNext()){
                String resURL = iterable.next().getAttribute();
                if(pathMatcher.match(resURL,url)){
                    return resourceMap.get(role);
                }
            }
        }*/
        while (ite.hasNext()) {
            String resURL = ite.next();
            System.out.println(resURL);
            System.out.println(url);
            System.out.println(pathMatcher.match(resURL,url));
            if (pathMatcher.match(resURL,url)) {
                System.out.println(resourceMap.get(resURL).size());
                return resourceMap.get(resURL);
            }
        }
        return resourceMap.get(url);
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
}












