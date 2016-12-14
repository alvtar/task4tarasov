package service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.AuthorDao;
import dao.PublicationDao;
import dao.ReaderDao;
import dao.UsageDao;

import domain.Publication;

import exception.PersistentException;

public class PublicationServiceImpl extends ServiceImpl implements PublicationService {

    
    
    @Override
    public Publication findById(Integer id) throws PersistentException {
        PublicationDao publicationDao = createDao(PublicationDao.class); //// <<<------ transaction - разобрать
        Publication publication = publicationDao.read(id);
        if (publication != null) {
            buildPublication(Arrays.asList(publication));
        }
        return publication;
    }   
    
    
    @Override
    public List<Publication> findByIssn(Integer issn) throws PersistentException {
        PublicationDao bookDao = createDao(PublicationDao.class);
        List<publication> publications = publicationDao.readByIssn(issn);
        buildPublication(publications);
        return publications;
    }


    @Override
    public List<publication> findByTitle(String search) throws PersistentException {
        PublicationDao bookDao = transaction.createDao(PublicationDao.class);
        List<publication> publications = publicationDao.readByTitle(search);
        buildPublication(publications);
        return publications;
    }




   

    @Override
    public void save(Publication publication) throws PersistentException {
        PublicationDao dao = createDao(PublicationDao.class);
        if (publication.getId() != null) {
            dao.update(publication);
        } else {
            publication.setId(dao.create(publication));
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        PublicationDao dao = createDao(PublicationDao.class);
        dao.delete(id);
    }




    private void buildBook(List<Book> books) throws PersistentException {
        AuthorDao authorDao = transaction.createDao(AuthorDao.class);
        UsageDao usageDao = transaction.createDao(UsageDao.class);
        ReaderDao readerDao = transaction.createDao(ReaderDao.class);
        Map<Integer, Author> authors = new HashMap<>();
        List<Usage> usages;
        Map<Integer, Reader> readers = new HashMap<>();
        Author author;
        Integer identity;
        Reader reader;
        for (Book book : books) {
            author = book.getAuthor();
            if (author != null) {
                identity = author.getIdentity();
                author = authors.get(identity);
                if (author == null) {
                    author = authorDao.read(identity);
                }
                book.setAuthor(author);
            }
            usages = usageDao.readByBook(book.getIdentity());
            for (Usage usage : usages) {
                if (usage.getReturnDate() == null) {
                    book.setCurrentUsage(usage);
                } else {
                    book.getUsages().add(usage);
                }
                identity = usage.getReader().getIdentity();
                reader = readers.get(identity);
                if (reader == null) {
                    reader = readerDao.read(identity);
                }
                usage.setReader(reader);
            }
        }
    }
}
