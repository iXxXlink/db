package com.dbtest.dbkeshe.repository;

import com.dbtest.dbkeshe.entity.User;

public interface UserRepository {
    public void register(User user);
    public User findByName(String username);
    public User check(User user);
}
