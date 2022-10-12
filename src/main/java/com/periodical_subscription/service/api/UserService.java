package com.periodical_subscription.service.api;

import com.periodical_subscription.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User update (User user);

    void delete(User user);


}
