package dao;

import java.util.List;

import domain.Subscription;
import exception.PersistentException;


public interface SubscriptionDao  extends Dao<Subscription> {
    Subscription readByPublicationId(Integer publicationId) throws PersistentException;
    Subscription readByUserId(Integer userId) throws PersistentException;
    Subscription readBySubsYear(Integer subsYear) throws PersistentException;
    List<Subscription> read() throws PersistentException;
}
