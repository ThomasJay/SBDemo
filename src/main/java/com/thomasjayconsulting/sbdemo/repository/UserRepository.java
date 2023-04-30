package com.thomasjayconsulting.sbdemo.repository;

import com.thomasjayconsulting.sbdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(name="select u from User u where u.email = :email", nativeQuery = false)
    User findByEmail(String email);
}
