package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.AuthorDao;
import dao.PublicationDao;
import dao.ReaderDao;
import dao.UsageDao;
import domain.Author;
import domain.Book;
import domain.Reader;
import domain.Usage;
import exception.PersistentException;

public class ReaderServiceImpl extends ServiceImpl implements ReaderService {
	@Override
	public Reader findByIdentity(Integer identity) throws PersistentException {
		ReaderDao readerDao = transaction.createDao(ReaderDao.class);
		Reader reader = readerDao.read(identity);
		buldReader(reader);
		return reader;
	}

	@Override
	public List<Reader> findAll() throws PersistentException {
		ReaderDao readerDao = transaction.createDao(ReaderDao.class);
		List<Reader> readers = readerDao.read();
		buildList(readers);
		return readers;
	}

	@Override
	public void save(Reader reader) throws PersistentException {
		ReaderDao dao = transaction.createDao(ReaderDao.class);
		if(reader.getIdentity() != null) {
			dao.update(reader);
		} else {
			reader.setIdentity(dao.create(reader));
		}
	}

	@Override
	public void delete(Integer identity) throws PersistentException {
		ReaderDao dao = transaction.createDao(ReaderDao.class);
		dao.delete(identity);
	}

	@Override
	public List<Reader> findBySurname(String search) throws PersistentException {
		ReaderDao readerDao = transaction.createDao(ReaderDao.class);
		List<Reader> readers = readerDao.read(search);
		buildList(readers);
		return readers;
	}

	@Override
	public Reader findByLibraryCardNumber(String libraryCardNumber) throws PersistentException {
		ReaderDao readerDao = transaction.createDao(ReaderDao.class);
		Reader reader = readerDao.readByLibraryCardNumber(libraryCardNumber);
		buldReader(reader);
		return reader;
	}

	private void buildList(List<Reader> readers) throws PersistentException {
		UsageDao usageDao = transaction.createDao(UsageDao.class);
		List<Usage> usagesList = usageDao.readOverdue();
		Map<Integer, List<Usage>> usagesMap = new HashMap<>();
		List<Usage> readerUsagesList;
		Integer readerIdentity;
		for(Usage usage : usagesList) {
			readerIdentity = usage.getReader().getIdentity();
			readerUsagesList = usagesMap.get(readerIdentity);
			if(readerUsagesList == null) {
				readerUsagesList = new ArrayList<>();
				usagesMap.put(readerIdentity, readerUsagesList);
			}
			readerUsagesList.add(usage);
		}
		for(Reader reader : readers) {
			readerIdentity = reader.getIdentity();
			readerUsagesList = usagesMap.get(readerIdentity);
			if(readerUsagesList != null) {
				reader.getOverdueUsages().addAll(readerUsagesList);
				for(Usage usage : readerUsagesList) {
					usage.setReader(reader);
				}
			}
		}
	}

	private void buldReader(Reader reader) throws PersistentException {
		UsageDao usageDao = transaction.createDao(UsageDao.class);
		PublicationDao bookDao = transaction.createDao(PublicationDao.class);
		Map<Integer, Book> books = new HashMap<>();
		AuthorDao authorDao = transaction.createDao(AuthorDao.class);
		Map<Integer, Author> authors = new HashMap<>();
		Integer bookIdentity;
		Book book;
		Integer authorIdentity;
		Author author;
		Date now = new Date();
		if(reader != null) {
			List<Usage> usages = usageDao.readByReader(reader.getIdentity());
			for(Usage usage : usages) {
				usage.setReader(reader);
				bookIdentity = usage.getBook().getIdentity();
				book = books.get(bookIdentity);
				if(book == null) {
					book = bookDao.read(bookIdentity);
					books.put(bookIdentity, book);
					if(book.getAuthor() != null) {
						authorIdentity = book.getAuthor().getIdentity();
						if(authorIdentity != null) {
							author = authors.get(authorIdentity);
							if(author == null) {
								author = authorDao.read(authorIdentity);
								authors.put(authorIdentity, author);
							}
							book.setAuthor(author);
						}
					}
				}
				usage.setBook(book);
				if(usage.getReturnDate() == null) {
					if(usage.getPlanReturnDate().before(now)) {
						reader.getOverdueUsages().add(usage);
					} else {
						reader.getCurrentUsages().add(usage);
					}
				} else {
					reader.getReturnedUsages().add(usage);
				}
			}
		}
	}
}
