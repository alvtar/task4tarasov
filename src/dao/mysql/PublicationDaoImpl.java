package dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import dao.PublicationDao;
import domain.Publication;
import exception.PersistentException;

public class PublicationDaoImpl extends BaseDaoImpl implements PublicationDao {
    private static Logger logger = Logger.getLogger(PublicationDaoImpl.class);

    // Create new publication
    @Override
    public Integer create(Publication publication) throws PersistentException {
        String sql = "INSERT INTO `publications` (`issn`, `title`, `monthCost`, `active`) "
                + "VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, publication.getIssn());
            statement.setString(2, publication.getTitle());
            statement.setFloat(3, publication.getMonthCost());
            statement.setBoolean(4, publication.getActive());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record "
                        + "into table `publications`");
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
    
    // Read publication by Id
    @Override
    public Publication read(Integer id) throws PersistentException {
        String sql = "SELECT `issn`, `title`, `monthCost`, `active`, "
                + "`lastUpdate` FROM `publications` WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            Publication publication = null;
            if (resultSet.next()) {
                publication = new Publication();
                publication.setId(id);
                publication.setIssn(resultSet.getInt("issn"));
                publication.setTitle(resultSet.getString("title"));
                publication.setMonthCost(resultSet.getFloat("monthCost"));
                publication.setActive(resultSet.getBoolean("active"));
                publication.setLastUpdate(resultSet.getDate("lastUpdate"));
            }
            return publication;
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

    // Update publication parameters
    @Override
    public void update(Publication publication) throws PersistentException {
        String sql = "UPDATE `publications` SET `title` = ?, "
                + "`monthCost` = ?, `active`=? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, publication.getTitle());
            statement.setFloat(2, publication.getMonthCost());
            statement.setBoolean(3, publication.getActive());
            statement.setInt(4, publication.getId());
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
    
    // Delete publication
    @Override
    public void delete(Integer id) throws PersistentException {
        String sql = "DELETE FROM `publications` WHERE `id` = ?";
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
    
    // Read publication by ISSN - International Standard Serial Number 
    // (also called 'Index' in ex-USSR countries)
    @Override
    public Publication readByIssn(Integer issn) throws PersistentException {
        String sql = "SELECT `id`, `issn`, `title`, `monthCost`, `active`, "
                + "`lastUpdate` FROM `publications` WHERE `issn` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, issn);
            resultSet = statement.executeQuery();
            Publication publication = null;
            if (resultSet.next()) {
                publication = new Publication();
                publication.setId(resultSet.getInt("id"));
                publication.setIssn(resultSet.getInt("issn"));
                publication.setTitle(resultSet.getString("title"));
                publication.setMonthCost(resultSet.getFloat("monthCost"));
                publication.setActive(resultSet.getBoolean("active"));
                publication.setLastUpdate(resultSet.getDate("lastUpdate"));
            }
            return publication;
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
    
    // Find publications by part of Title
    @Override
    public List<Publication> readByTitleLike(String titleLike) throws PersistentException {
        String sql = "SELECT `id`, `issn`, `title`, `monthCost`, `active`, `lastUpdate` "
                + "FROM `publications` WHERE `title` LIKE ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, titleLike);
            resultSet = statement.executeQuery();
            List<Publication> publications = new ArrayList<>();
            Publication publication = null;
            while (resultSet.next()) {
                publication = new Publication();
                publication.setId(resultSet.getInt("id"));
                publication.setIssn(resultSet.getInt("issn"));
                publication.setTitle(resultSet.getString("title"));
                publication.setMonthCost(resultSet.getFloat("monthCost"));
                publication.setActive(resultSet.getBoolean("active"));
                publication.setLastUpdate(resultSet.getDate("lastUpdate"));
                publications.add(publication);
            }
            return publications;
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
    
    // Read all publications 
    @Override
    public List<Publication> read() throws PersistentException {
        String sql = "SELECT `id`, `issn`, `title`, `monthCost`, `active`, "
                + "`lastUpdate` FROM `publications` ORDER BY `id`";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            List<Publication> publications = new ArrayList<>();
            Publication publication = null;
            while (resultSet.next()) {
                publication = new Publication();
                publication.setId(resultSet.getInt("id"));
                publication.setIssn(resultSet.getInt("issn"));
                publication.setTitle(resultSet.getString("title"));
                publication.setMonthCost(resultSet.getFloat("monthCost"));
                publication.setActive(resultSet.getBoolean("active"));
                publication.setLastUpdate(resultSet.getDate("lastUpdate"));
                publications.add(publication);
            }
            return publications;
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
