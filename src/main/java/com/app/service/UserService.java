package com.app.service;

import com.app.models.jpa.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Class UserService
 * Service that handle UserController
 * Use @Autowired for connect to necessary repositories
 *
 * Created by Kulinenko Roman
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Page<User> getUsers(Integer page, Integer perPage) {
        return userRepository.findAll(new PageRequest(page - 1, perPage, Sort.Direction.DESC, "id"));
    }

    public void insertUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.delete(id);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id);
    }
}
