package service;

import java.util.List;

import dao.*;
import dao.mysql.*;
import domain.*;
import exception.PersistentException;


public class PublicationServiceImpl implements PublicationService {

    @Override
    public List<Publication> findAll() throws PersistentException {
        
        PublicationDao dao = new PublicationDaoImpl();
        return dao.read();
    }

    @Override
    public Publication findById(Integer id) throws PersistentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Publication findByIssn(Integer issn) throws PersistentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(Publication publication) throws PersistentException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        // TODO Auto-generated method stub
        
    }

}
