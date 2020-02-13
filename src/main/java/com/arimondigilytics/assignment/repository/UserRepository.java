package com.arimondigilytics.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.arimondigilytics.assignment.orm.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
