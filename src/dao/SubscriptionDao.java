package dao;

import java.util.List;

import domain.Subscription;
import exception.PersistentException;


public interface SubscriptionDao  extends Dao<Subscription> {
    Subscription readBySubsYear(Integer subsYear) throws PersistentException;
    List<Subscription> read() throws PersistentException;
}
