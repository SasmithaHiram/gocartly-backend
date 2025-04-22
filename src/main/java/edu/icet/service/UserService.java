package edu.icet.service;

import edu.icet.dto.User;

import java.util.List;

public interface UserService {
    boolean createUser(User user);
    User searchUserById(Long id);
    boolean updateUser(User user);
    boolean deleteUser(Long id);
    List<User> getAll();
}
