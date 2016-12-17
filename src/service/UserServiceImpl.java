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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(User user) throws PersistentException {
        UserDao dao = new UserDaoImpl();;
        if(user.getId() != null) {
                //if(user.getPassword() != null) {
                        //user.setPassword(user.getPassword());
                //} else {
                        //User oldUser = dao.read(user.getIdentity());
                        //user.setPassword(oldUser.getPassword());
                //}
            
                dao.update(user);
        } else {
                //user.setPassword(md5(new String()));
                user.setId(dao.create(user));
        }
        
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        // TODO Auto-generated method stub
        
    }

}
