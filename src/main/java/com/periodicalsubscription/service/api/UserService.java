package com.periodicalsubscription.service.api;

import com.periodicalsubscription.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User update (User user);

    void delete(User user);


}
