package org.fms.mysql.repository;

import org.fms.mysql.entity.Permission;
import org.fms.mysql.entity.Role;
import org.fms.mysql.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
public class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;


    @Test
    public void find(){
        permissionRepository.findAll().stream().forEach(role -> {
            System.err.println(role);
        });
    }
}
