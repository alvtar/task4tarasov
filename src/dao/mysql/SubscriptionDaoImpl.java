package dao.mysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dao.SubscriptionDao;
import domain.Subscription;
import exception.PersistentException;


public class SubscriptionDaoImpl extends BaseDaoImpl implements SubscriptionDao {

    private static Logger logger = Logger.getLogger(SubscriptionDaoImpl.class);

    @Override
    public Integer create(Subscription subscription) throws PersistentException {
        String sql = "INSERT INTO `subscriptions` (`regDate`, `user_id`, `issn_id`, `subsYear`, `subsMonths`, `paymentSum`) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            
         // ???????????????? ID
            statement.setInt(2, subscription.getUser().getId());
            statement.setInt(3, subscription.getPublication().getId());
            
      // ???????????????? Year
            
            statement.setDate(4, new java.sql.Date(subscription.getSubsYear().getValue()));
            statement.setInt(5, subscription.getSubsMonths());
            
         // ???????????????? Sum
            statement.setFloat(6, subscription.getPaymentSum());

            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `subscriptions`");
                throw new PersistentException();
            }
        } catch (SQLException e) {
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
        }
    }

    
    
    @Override
    public Subscription read(Integer id) throws PersistentException {
        String sql = "SELECT `regDate`, `user_id`, `issn_id`, `subsYear`, `subsMonths`, `paymentSum` FROM `subscriptions` WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Subscription subscription = null;
            if (resultSet.next()) {
                subscription = new Subscription();
                subscription.setId(id);
                subscription.setUser(resultSet.getInt("user_id"));
                subscription.setPublication(resultSet.getInt("issn_id"));
                /////subscription.setRole(Role.getById(resultSet.getInt("role")));
                
               // new java.sql.Date(subscription.getSubsYear().getValue());
           // ????????? Year     
                //subscription.setSubsYear((resultSet.getDate("subsYear").toLocalDate().getYear()));
                //subscription.setSubsYear(new java.sql.Date(resultSet.getDate("subsYear").toLocalDate().getYear()));
                
                subscription.setSubsMonths(resultSet.getInt("subsMonths"));
                subscription.setPaymentSum(resultSet.getFloat("paymentSum"));
            }
            return subscription;
        } catch (SQLException e) {
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
        }
    }

    @Override
    public void update(Subscription subscription) throws PersistentException {
        String sql = "UPDATE `subscriptions` SET `login` = ?, `password` = ?, `role` = ?, `fullName`=?, `zipCode`=?, `address`=? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, subscription.getLogin());
            statement.setString(2, subscription.getPassword());
            statement.setInt(3, subscription.getRole().getId());
            statement.setString(4, subscription.getFullName());
            statement.setInt(5, subscription.getZipCode());
            statement.setString(6, subscription.getAddress());
            statement.setInt(7, subscription.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        String sql = "DELETE FROM `subscriptions` WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
            }
        }
    }

    @Override
    public Subscription readByLogin(String login) throws PersistentException {
        String sql = "SELECT `login`, `password`, `role`, `fullName`, `zipCode`, `address` FROM `subscriptions` WHERE `login` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            Subscription subscription = null;
            if (resultSet.next()) {
                subscription = new Subscription();
                subscription.setId(resultSet.getInt("id"));
                subscription.setLogin(login);
                subscription.setPassword(resultSet.getString("password"));
                subscription.setRole(Role.getById(resultSet.getInt("role")));
                subscription.setFullName(resultSet.getString("fullName"));
                subscription.setZipCode(resultSet.getInt("zipCode"));
                subscription.setAddress(resultSet.getString("address"));
            }
            return subscription;
        } catch (SQLException e) {
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
        }
    }

    @Override
    public List<Subscription> read() throws PersistentException {
        String sql = "SELECT `identity`, `login`, `password`, `role`, `fullName`, `zipCode`, `address` FROM `subscriptions` ORDER BY `login`";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            List<Subscription> subscriptions = new ArrayList<>();
            Subscription subscription = null;
            while (resultSet.next()) {
                subscription = new Subscription();
                subscription.setId(resultSet.getInt("id"));
                subscription.setLogin(resultSet.getString("login"));
                subscription.setPassword(resultSet.getString("password"));
                subscription.setRole(Role.getById(resultSet.getInt("role")));
                subscription.setFullName(resultSet.getString("fullName"));
                subscription.setZipCode(resultSet.getInt("zipCode"));
                subscription.setAddress(resultSet.getString("address"));
                subscriptions.add(subscription);
            }
            return subscriptions;
        } catch (SQLException e) {
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
        }
    }

    
    
}
