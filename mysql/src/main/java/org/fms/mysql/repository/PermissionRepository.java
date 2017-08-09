package org.fms.mysql.repository;

import org.fms.mysql.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lion on 2017/7/5.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long>{
}
