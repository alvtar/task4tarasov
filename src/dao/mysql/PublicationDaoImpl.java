package dao.mysql;

import java.util.List;

import dao.PublicationDao;
import domain.Publication;
import exception.PersistentException;


public class PublicationDaoImpl  extends BaseDaoImpl implements PublicationDao {

    @Override
    public Integer create(Publication entity) throws PersistentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Publication read(Integer id) throws PersistentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Publication entity) throws PersistentException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Publication readById(Integer id) throws PersistentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Publication readByIssn(Integer issn) throws PersistentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Publication> readByTitle(String title) throws PersistentException {
        // TODO Auto-generated method stub
        return null;
    }

}
