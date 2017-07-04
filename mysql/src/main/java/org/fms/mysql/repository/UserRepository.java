package org.fms.mysql.repository;

import org.fms.mysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lion on 2017/6/27.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//	User findByUserid(Long userID);
	User findByName(String name);
}
