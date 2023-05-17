package org.example.dao;

import org.example.entity.User;

import java.util.List;

public interface UserDAO {

    public boolean addUser(User user);

    public List<User> getAllUsers();

    public boolean deleteUser(Long userId);

}
