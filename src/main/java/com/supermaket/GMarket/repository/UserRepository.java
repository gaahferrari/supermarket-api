package com.supermaket.GMarket.repository;

import com.supermaket.GMarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findUserByUserNameAndPassword(String userName, String password);

}
