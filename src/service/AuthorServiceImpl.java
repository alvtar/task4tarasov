package service;

import java.util.List;
import java.util.Map;

import dao.AuthorDao;
import domain.Author;
import exception.PersistentException;

public class AuthorServiceImpl extends ServiceImpl implements AuthorService {
	@Override
	public List<Author> findAll() throws PersistentException {
		AuthorDao dao = transaction.createDao(AuthorDao.class);
		return dao.read();
	}

	@Override
	public Author findByIdentity(Integer identity) throws PersistentException {
		AuthorDao authorDao = transaction.createDao(AuthorDao.class);
		return authorDao.read(identity);
	}

	@Override
	public void save(Author author) throws PersistentException {
		AuthorDao dao = transaction.createDao(AuthorDao.class);
		if(author.getIdentity() != null) {
			dao.update(author);
		} else {
			author.setIdentity(dao.create(author));
		}
	}

	@Override
	public void delete(Integer identity) throws PersistentException {
		AuthorDao dao = transaction.createDao(AuthorDao.class);
		dao.delete(identity);
	}

	@Override
	public Map<Author, Integer> findAllWithNumberOfBooks() throws PersistentException {
		AuthorDao dao = transaction.createDao(AuthorDao.class);
		return dao.readWithNumberOfBooks();
	}
}
