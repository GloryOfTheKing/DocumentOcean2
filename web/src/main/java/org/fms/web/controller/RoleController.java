package org.fms.web.controller;

import org.fms.mysql.entity.Role;
import org.fms.mysql.model.RoleQo;
import org.fms.mysql.repository.RoleRepository;
import org.fms.web.utils.ContentResults;
import org.fms.web.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lion on 2017/8/9.
 */
@RestController("/role")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(path = "/findAll",method = RequestMethod.GET)
    public ContentResults<Page> findAll(RoleQo roleQo){
    Pageable pageable = new PageRequest(roleQo.getPage(), roleQo.getSize(), new Sort(Sort.Direction.ASC, "id"));
        Page<Role> rolePage = roleRepository.findAll(pageable);
        return new ContentResults<Page>(200,"find Roles",rolePage);
    }


    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public ContentResults<Role> add(Role role){
        Role addRole= roleRepository.save(role);
        return new ContentResults<Role>(200,"Add role success",addRole);
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.DELETE)
    public Results delete(@PathVariable Long id){
        roleRepository.delete(id);
        return new Results(200,"Delete role success");
    }

    @RequestMapping(path = "/edit",method = RequestMethod.PUT)
    public ContentResults<Role> updata(Role role){
        Role role1 = roleRepository.save(role);
        return new ContentResults<Role>(200,"Updata role sucess",role1);
    }

    @RequestMapping(path = "/find/{id}",method = RequestMethod.GET)
    public ContentResults<Role> find(@PathVariable long id){
        Role role = roleRepository.findOne(id);
        if(role == null){
            return new ContentResults<Role>(404,"Can't find the Role",null);
        }
        return new ContentResults<Role>(200,"Can't find the Role",role);
    }
}









