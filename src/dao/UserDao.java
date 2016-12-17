package dao;

import java.util.List;
import domain.User;
import exception.PersistentException;

public interface UserDao extends Dao<User> {
    /// User readByLogin(String login) throws PersistentException;
    List<User> read() throws PersistentException;

    User findByLoginAndPassword(String login, String password) throws PersistentException;

    boolean chechUnique(String login) throws PersistentException;
}
