package org.fms.mysql.repository;

import org.fms.mysql.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by lion on 2017/6/27.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	User findByName(String name);
	@Query("select t from User t where t.name like :name")
	Page<User> findByName(@Param("name") String name, Pageable pageRequest);

}
