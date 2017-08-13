package org.fms.web.controller.system;

import org.fms.mysql.entity.Department;
import org.fms.mysql.repository.DepartmentRepository;
import org.fms.web.utils.ContentResults;
import org.fms.web.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lion on 2017/8/9.
 */
@RestController
@RequestMapping("/dept")
public class DepartmentController {

    @Autowired
    DepartmentRepository departmentRepository;

    

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public ContentResults<Department> add(Department department){
        Department department1 =  departmentRepository.save(department);
        return new ContentResults<Department>(200,"Add department success",department1);
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.DELETE)
    public Results delete(@PathVariable Long id){
        departmentRepository.delete(id);
        return new Results(200,"Delete department success");
    }

    @RequestMapping(path = "/edit",method = RequestMethod.PUT)
    public ContentResults<Department> updata(Department department){
        Department department1 = departmentRepository.save(department);
        return new ContentResults<Department>(200,"Updata department success",department1);
    }

    @RequestMapping(path = "/find/{id}",method = RequestMethod.GET)
    public ContentResults<Department> find(@PathVariable Long id){
        Department department = departmentRepository.findOne(id);
        if(department == null){
            return new ContentResults<Department>(404,"Can't find the department",null);
        }
        return new ContentResults<Department>(200,"Find the department",department);
    }
}














