package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByName(String username) {
        return userRepository.findByUserName(username).get();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }
//    @Override
//    @Transactional
//    public void upDate(User user) {
//        User oldUser = userRepository.findById(user.getId()).get();
//        String oldPassword = oldUser.getPassword();
//        if (user.getPassword() == null || user.getPassword().isEmpty()) {
//            user.setPassword(oldPassword);
//        } else {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
//        userRepository.save(user);
//    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(getUser(id));
    }

    @Override
    @Transactional
    public void upDate(User user) {
            Optional<User> userFromDB = userRepository.findById(user.getId());
            String newPassword = user.getPassword();
            String currentPassword = userFromDB.get().getPassword();

            if (!currentPassword.equals(newPassword)) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            userRepository.save(user);
        }
}

