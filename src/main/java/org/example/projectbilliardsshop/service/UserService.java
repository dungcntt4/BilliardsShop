package org.example.projectbilliardsshop.service;

import org.example.projectbilliardsshop.model.Role;
import org.example.projectbilliardsshop.model.User;
import org.example.projectbilliardsshop.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Boolean signUp(User user) {
        user.setRole(Role.USER);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
        return (userRepository.save(user) != null);
    }

    public Optional<User> findByEmail(User user) {
        return Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
    }

    public User login(User user){
        User userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional!=null) {
            if(BCrypt.checkpw(user.getPassword(), userOptional.getPassword())) {
                return userOptional;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    public User findById(int id) {
        return userRepository.findById(id);
    }
}
