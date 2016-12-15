package dao;

import java.time.Year;
import java.util.List;

import domain.Subscription;
import domain.User;
import exception.PersistentException;

public interface SubscriptionDao  extends Dao<Subscription> {
    User readBySubsYear(Year subsYear) throws PersistentException;
    List<Subscription> read() throws PersistentException;
}
