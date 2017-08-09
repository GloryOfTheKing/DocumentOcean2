package org.fms.web.controller;

import org.fms.mysql.entity.User;
import org.fms.mysql.model.UserQo;
import org.fms.mysql.repository.UserRepository;
import org.fms.web.utils.ContentResults;
import org.fms.web.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



/**
 * Created by lion on 2017/8/8.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/findAll",method = RequestMethod.GET)
    public ContentResults<Page> findAll(UserQo userQo){
        Pageable pageable = new PageRequest(userQo.getPage(), userQo.getSize(), new Sort(Sort.Direction.ASC, "id"));
        Page<User> userPage = userRepository.findAll(pageable);
        return new ContentResults<Page>(200,"Find Users",userPage);
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
