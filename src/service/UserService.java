package service;

import java.util.List;
import domain.User;
import exception.PersistentException;

public interface UserService {
    List<User> findAll() throws PersistentException;

    User findById(Integer id) throws PersistentException;

    User findByLoginAndPassword(String login, String password) throws PersistentException;

    boolean isUniqueLogin(String login) throws PersistentException;

    void save(User user) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
