package service;

import java.util.List;

import domain.User;
import exception.PersistentException;

public interface UserService extends Service {
	List<User> findAll() throws PersistentException;

	User findById(Integer id) throws PersistentException;

	User findByLoginAndPassword(String login, String password) throws PersistentException;

	void save(User user) throws PersistentException;

	void delete(Integer id) throws PersistentException;
}
