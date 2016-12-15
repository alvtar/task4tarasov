package dao;

import java.util.List;


import domain.User;
import exception.PersistentException;

public interface UserDao extends Dao<User> {
	User readByLogin(String login) throws PersistentException;
	List<User> read() throws PersistentException;
}
