package service;

import java.util.List;

import domain.Publication;
import exception.PersistentException;

public interface PublicationService extends Service {
	List<Publication> findByIssn(Integer issn) throws PersistentException;

	List<Publication> findByTitle(String title) throws PersistentException;

	Publication findById(Integer id) throws PersistentException;


	void save(Publication publication) throws PersistentException;

	void delete(Integer id) throws PersistentException;
}
