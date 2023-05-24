package com.bravo.johny.service;

import com.bravo.johny.dto.Role;
import com.bravo.johny.dto.User;

import java.util.List;

public interface UserService {

    boolean authenticateUser(User user);

    User addNewUser(User user);

    List<User> getAllUsers(int offset, int limit);

    List<User> getUsers(String lastName, int offset, int limit);

    User getUserDetails(String userName);

    User updateUserDetails(String userName, User user);

    User updateUserRole(String userName, Role role);

    void deleteUser(String userName);
}
