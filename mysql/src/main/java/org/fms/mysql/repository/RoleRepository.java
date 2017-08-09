package org.fms.mysql.repository;

import org.fms.mysql.entity.Role;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lion on 2017/7/5.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
