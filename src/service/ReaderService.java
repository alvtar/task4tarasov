package service;

import java.util.List;

import domain.Reader;
import exception.PersistentException;

public interface ReaderService extends Service {
	Reader findByIdentity(Integer identity) throws PersistentException;

	Reader findByLibraryCardNumber(String libraryCardNumber) throws PersistentException;

	List<Reader> findAll() throws PersistentException;

	List<Reader> findBySurname(String search) throws PersistentException;

	void save(Reader reader) throws PersistentException;

	void delete(Integer identity) throws PersistentException;
}
