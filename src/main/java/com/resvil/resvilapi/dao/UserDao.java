package com.resvil.resvilapi.dao;

import com.resvil.resvilapi.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer>
{
}

