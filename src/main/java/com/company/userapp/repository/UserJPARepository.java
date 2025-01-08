package com.company.userapp.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.company.userapp.models.User;

// db integration

@Repository
public interface UserJPARepository extends JpaRepository<User, Integer> {

	List<User> findByTitle(String title);
	
	@Query(value= "select title from User t where t.id=1")
	Collection<User> findAllUsers();
}
