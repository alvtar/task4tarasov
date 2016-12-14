package service;

import java.util.List;
import java.util.Map;

import domain.Author;
import exception.PersistentException;

public interface AuthorService extends Service {
	List<Author> findAll() throws PersistentException;

	Map<Author, Integer> findAllWithNumberOfBooks() throws PersistentException;

	Author findByIdentity(Integer identity) throws PersistentException;

	void save(Author author) throws PersistentException;

	void delete(Integer identity) throws PersistentException;
}
