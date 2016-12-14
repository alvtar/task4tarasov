package dao;

import java.util.List;

import domain.Publication;
import exception.PersistentException;

public interface PublicationDao extends Dao<Publication> {
        Publication readById(Integer id) throws PersistentException;
	Publication readByIssn(Integer issn) throws PersistentException;
	List<Publication> readByTitle(String title) throws PersistentException;
}
