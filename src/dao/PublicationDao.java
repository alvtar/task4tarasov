package dao;

import java.util.List;

import domain.Publication;
import exception.PersistentException;

public interface PublicationDao extends Dao<Publication> {
    Publication readByIssn(Integer issn) throws PersistentException;

    List<Publication> readByTitleLike(String titleLike) throws PersistentException;

    List<Publication> read() throws PersistentException;
}
