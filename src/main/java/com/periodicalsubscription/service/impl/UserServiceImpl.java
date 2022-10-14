package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.model.repository.UserRepository;
import com.periodicalsubscription.model.entity.User;
import com.periodicalsubscription.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.orElseThrow(RuntimeException::new);
    }

    @Override
    public User save(User user) {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("User with this email already exists");
        }
        //TODO some other validation
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        //TODO some validation?
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        //TODO some validation?
        userRepository.delete(user);
    }

}
