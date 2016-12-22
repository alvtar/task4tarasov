package dao.mysql;

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

    
    /*public SubscriptionDaoImpl () {
        Connection conn=null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
        } catch (PersistentException e) {
        }
        setConnection(conn);
    }*/
    
    
    @Override
    public Integer create(Subscription subscription) throws PersistentException {
        String sql = "INSERT INTO `subscriptions` (`regDate`, `userId`, `publicationId`, `subsYear`, `subsMonths`, `paymentSum`) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            
         // ???????????????? ID
            statement.setInt(2, subscription.getUserId());
            statement.setInt(3, subscription.getPublicationId());
            
            // ???????????????? Year
            //statement.setDate(4, new java.sql.Date(subscription.getSubsYear().getValue()));
            statement.setInt(4, subscription.getSubsYear());
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
        } catch (SQLException  | NullPointerException e) {
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

    
    
    @Override
    public Subscription read(Integer id) throws PersistentException {
        String sql = "SELECT `regDate`, `userId`, `publicationId`, `subsYear`, `subsMonths`, `paymentSum` FROM `subscriptions` WHERE `id` = ?";
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
                subscription.setRegDate(resultSet.getDate("regDate"));
                subscription.setUserId(resultSet.getInt("userId"));
                subscription.setPublicationId(resultSet.getInt("publicationId"));
                subscription.setSubsYear(resultSet.getInt("subsYear"));
                
               // new java.sql.Date(subscription.getSubsYear().getValue());
           // ????????? Year     
                //subscription.setSubsYear((resultSet.getDate("subsYear").toLocalDate().getYear()));
                //subscription.setSubsYear(new java.sql.Date(resultSet.getDate("subsYear").toLocalDate().getYear()));
                
                subscription.setSubsMonths(resultSet.getInt("subsMonths"));
                subscription.setPaymentSum(resultSet.getFloat("paymentSum"));
            }
            return subscription;
        } catch (SQLException  | NullPointerException e) {
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

    @Override
    public void update(Subscription subscription) throws PersistentException {
        String sql = "UPDATE `subscriptions` SET `userId` = ?, `publicationId` = ?, `subsYear` = ?, `subsMonths`=?, `paymentSum`=? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, subscription.getUserId());
            statement.setInt(2, subscription.getPublicationId());
            statement.setInt(3, subscription.getSubsYear());
            statement.setInt(4, subscription.getSubsMonths());
            statement.setFloat(5, subscription.getPaymentSum());
            statement.setInt(7, subscription.getId());
            statement.executeUpdate();
        } catch (SQLException  | NullPointerException e) {
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

    @Override
    public void delete(Integer id) throws PersistentException {
        String sql = "DELETE FROM `subscriptions` WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException  | NullPointerException e) {
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

    
    
    
    @Override
    public Subscription readByPublicationId(Integer publicationId) throws PersistentException {
        String sql = "SELECT `id`, `regDate`, `userId`, `publicationId`, `subsYear`, `subsMonths`, `paymentSum` FROM `subscriptions` WHERE `publicationId` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, publicationId);
            resultSet = statement.executeQuery();
            Subscription subscription = null;
            if (resultSet.next()) {
                subscription = new Subscription();
                subscription.setId(resultSet.getInt("id"));
                subscription.setRegDate(resultSet.getDate("regDate"));
                subscription.setUserId(resultSet.getInt("userId"));
                subscription.setPublicationId(resultSet.getInt("publicationId"));
                subscription.setSubsYear(resultSet.getInt("subsYear"));
                subscription.setSubsMonths(resultSet.getInt("subsMonths"));
                subscription.setPaymentSum(resultSet.getFloat("paymentSum"));
            }
            return subscription;
        } catch (SQLException  | NullPointerException e) {
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
    
    
    
    @Override
    public Subscription readByUserId(Integer userId) throws PersistentException {
        String sql = "SELECT `id`, `regDate`, `userId`, `publicationId`, `subsYear`, `subsMonths`, `paymentSum` FROM `subscriptions` WHERE `userId` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            Subscription subscription = null;
            if (resultSet.next()) {
                subscription = new Subscription();
                subscription.setId(resultSet.getInt("id"));
                subscription.setRegDate(resultSet.getDate("regDate"));
                subscription.setUserId(resultSet.getInt("userId"));
                subscription.setPublicationId(resultSet.getInt("publicationId"));
                subscription.setSubsYear(resultSet.getInt("subsYear"));
                subscription.setSubsMonths(resultSet.getInt("subsMonths"));
                subscription.setPaymentSum(resultSet.getFloat("paymentSum"));
            }
            return subscription;
        } catch (SQLException  | NullPointerException e) {
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
    
    
    
    @Override
    public Subscription readBySubsYear(Integer subsYear) throws PersistentException {
        String sql = "SELECT `id`, `regDate`, `userId`, `publicationId`, `subsYear`, `subsMonths`, `paymentSum` FROM `subscriptions` WHERE `subYear` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, subsYear);
            resultSet = statement.executeQuery();
            Subscription subscription = null;
            if (resultSet.next()) {
                subscription = new Subscription();
                subscription.setId(resultSet.getInt("id"));
                subscription.setRegDate(resultSet.getDate("regDate"));
                subscription.setUserId(resultSet.getInt("userId"));
                subscription.setPublicationId(resultSet.getInt("publicationId"));
                subscription.setSubsYear(resultSet.getInt("subsYear"));
                subscription.setSubsMonths(resultSet.getInt("subsMonths"));
                subscription.setPaymentSum(resultSet.getFloat("paymentSum"));
            }
            return subscription;
        } catch (SQLException  | NullPointerException e) {
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

    @Override
    public List<Subscription> read() throws PersistentException {
        String sql = "SELECT `id`,`regDate`, `userId`, `publicationId`, `subsYear`, `subsMonths`, `paymentSum` FROM `subscriptions` ORDER BY `regDate`";
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
                subscription.setRegDate(resultSet.getDate("regDate"));
                subscription.setUserId(resultSet.getInt("userId"));
                subscription.setPublicationId(resultSet.getInt("publicationId"));
                subscription.setSubsYear(resultSet.getInt("subsYear"));
                subscription.setSubsMonths(resultSet.getInt("subsMonths"));
                subscription.setPaymentSum(resultSet.getFloat("paymentSum"));
                subscriptions.add(subscription);
            }
            return subscriptions;
        } catch (SQLException  | NullPointerException e) {
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
