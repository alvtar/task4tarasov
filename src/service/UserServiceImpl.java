package service;

import java.util.List;
import dao.UserDao;
import dao.mysql.UserDaoImpl;
import domain.User;
import exception.PersistentException;


public class UserServiceImpl implements UserService {

    @Override
    public List<User> findAll() throws PersistentException {
        UserDao dao = new UserDaoImpl();
        return dao.read();
    }

    @Override
    public User findById(Integer id) throws PersistentException {
        UserDao dao = new UserDaoImpl();
        return dao.read(id);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws PersistentException {
        UserDao dao = new UserDaoImpl();
        return dao.readByLoginAndPassword(login, password);
    }

    @Override
    public boolean isUniqueLogin(String login) throws PersistentException {
        UserDao dao = new UserDaoImpl();
        return dao.checkUnique(login);
    }

    @Override
    public void save(User user) throws PersistentException {
        UserDao dao = new UserDaoImpl();
        if (user.getId() != null) {
            dao.update(user);
        } else {
            user.setId(dao.create(user));
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        UserDao dao = new UserDaoImpl();
        dao.delete(id);
    }
}
