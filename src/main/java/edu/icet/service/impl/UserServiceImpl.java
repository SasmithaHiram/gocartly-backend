package edu.icet.service.impl;

import edu.icet.dto.User;
import edu.icet.entity.UserEntity;
import edu.icet.repository.UserRepository;
import edu.icet.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean createUser(User user) {
        if (user != null) {
            userRepository.save(modelMapper.map(user, UserEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public User searchUserById(Long id) {
        if (id != null) {
            return modelMapper.map(userRepository.findById(id), User.class);
        } else {
            return null;
        }
    }

    @Override
    public boolean updateUser(User user) {
        if (user != null) {
            return this.createUser(user);
        }
        return false;
    }

    @Override
    public boolean deleteUser(Long id) {
        if (id != null) {
            userRepository.deleteById(id);
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll().stream().map(userEntity ->
                modelMapper.map(userEntity, User.class)).toList();
    }

}
