package service;

import java.util.List;

import domain.User;
import exception.PersistentException;


public interface UserService {
    List<User> findAll() throws PersistentException;
    
    
    User findById(Integer id) throws PersistentException;//читает пользователя по id
    
    
    
    void save(User user) throws  PersistentException;//сохраняет нового пользователя
    void delete(Integer id) throws PersistentException;//удаляет пользователя
    User readUser(String login, String password) throws PersistentException ;//чтение пользователя по логину и паролю
    boolean chechUnique(String login) throws PersistentException;// проверяет уникальность логина,возвращает true,если логин уникальный

}
