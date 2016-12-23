package dao;

import java.util.List;
import domain.User;
import exception.PersistentException;

public interface UserDao extends Dao<User> {
    List<User> read() throws PersistentException;

    User readByLoginAndPassword(String login, String password) throws PersistentException;

    boolean checkUnique(String login) throws PersistentException;
}
