package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<User> getUsers();
    User updateUser(User user);
    void deleteUser(Long id);
    User getUser(Long id);
}
