package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.model.dao.UserDao;
import com.periodicalsubscription.model.entity.User;
import com.periodicalsubscription.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userDao.findById(id);

        return user.orElseThrow(RuntimeException::new);
    }

    @Override
    public User save(User user) {
        if(userDao.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("User with this email already exists");
        }
        //TODO some other validation
        return userDao.save(user);
    }

    @Override
    public User update(User user) {
        //TODO some validation?
        return userDao.save(user);
    }

    @Override
    public void delete(User user) {
        //TODO some validation?
        userDao.delete(user);
    }

}
