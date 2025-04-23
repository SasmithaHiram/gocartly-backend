package edu.icet.service;

import edu.icet.dto.User;

import java.util.List;

public interface UserService {
    boolean create(User user);
    User searchById(Long id);
    boolean update(User user);
    Boolean delete(Long id);
    List<User> getAll();
}
