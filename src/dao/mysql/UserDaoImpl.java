package dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import dao.UserDao;
import domain.Role;
import domain.User;
import exception.PersistentException;


public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoImpl.class);

    // Create new user
    @Override
    public Integer create(User user) throws PersistentException {
        String sql = "INSERT INTO `users` (`login`, `password`, `role`, "
                + "`fullName`, `zipCode`, `address`) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getId());
            statement.setString(4, user.getFullName());
            statement.setInt(5, user.getZipCode());
            statement.setString(6, "'" + user.getAddress() + "'");
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add "
                        + "record into table `users`");
                throw new PersistentException();
            }
        } catch (SQLException | NullPointerException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                connection.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    // Read user by Id
    @Override
    public User read(Integer id) throws PersistentException {
        String sql = "SELECT `login`, `password`, `role`, `fullName`, "
                + "`zipCode`, `address` FROM `users` WHERE `id` = ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getById(resultSet.getInt("role")));
                user.setFullName(resultSet.getString("login"));
                user.setZipCode(resultSet.getInt("zipCode"));
                user.setAddress(resultSet.getString("address"));
            }
            return user;
        } catch (SQLException | NullPointerException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                connection.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    // Update user parameters
    @Override
    public void update(User user) throws PersistentException {
        String sql = "UPDATE `users` SET `login` = ?, `password` = ?, "
                + "`role`=?, `fullName`=?, `zipCode`=?, `address`=? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3,1); // default SUBSRIBER role
            statement.setString(4, user.getFullName());
            statement.setInt(5, user.getZipCode());
            statement.setString(6, user.getAddress());
            statement.setInt(7, user.getId());
            statement.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                connection.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    // Delete user
    @Override
    public void delete(Integer id) throws PersistentException {
        String sql = "DELETE FROM `users` WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                connection.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }
    
    // Read user by login and password
    @Override
    public User readByLoginAndPassword(String login, String password) throws PersistentException {
        String sql = "SELECT `id`, `login`, `password`, `role`, `fullName`, `zipCode`, "
                + "`address` FROM `users` WHERE `login` = ? AND `password` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(login);
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getById(resultSet.getInt("role")));
                user.setFullName(resultSet.getString("fullName"));
                user.setZipCode(resultSet.getInt("zipCode"));
                user.setAddress(resultSet.getString("address"));
            }
            return user;
        } catch (SQLException | NullPointerException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                connection.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    // Read all users
    @Override
    public List<User> read() throws PersistentException {
        String sql = "SELECT `id`, `login`, `password`, `role`, `fullName`, "
                + "`zipCode`, `address` FROM `users` ORDER BY `fullName`";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            User user = null;
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getById(resultSet.getInt("role")));
                user.setFullName(resultSet.getString("fullName"));
                user.setZipCode(resultSet.getInt("zipCode"));
                user.setAddress(resultSet.getString("address"));
                users.add(user);
            }
            return users;
        } catch (SQLException | NullPointerException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                connection.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    // Check user login for unique
    @Override
    public boolean checkUnique(String login) throws PersistentException {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("SELECT `id` FROM `users` WHERE `login` = ?");
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            return !resultSet.next();
        } catch (SQLException | NullPointerException e) {
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
            try {
                connection.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }
}
