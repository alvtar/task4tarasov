package dao;

import java.util.List;
import domain.Subscription;
import exception.PersistentException;

public interface SubscriptionDao extends Dao<Subscription> {
    List<Subscription> readByPublicationId(Integer publicationId) throws PersistentException;

    List<Subscription> readByUserId(Integer userId) throws PersistentException;

    List<Subscription> readBySubsYear(Integer subsYear) throws PersistentException;

    List<Subscription> read() throws PersistentException;
}
