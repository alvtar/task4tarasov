package service;

import java.util.List;

import domain.Subscription;
import exception.PersistentException;

public interface SubscriptionService {
    List<Subscription> findAll() throws PersistentException;

    Subscription findById(Integer id) throws PersistentException;

    List<Subscription> findByUserId(Integer userId) throws PersistentException;
    
    Subscription findByPublicationId(Integer publicationId) throws PersistentException;
    
    Subscription findBySubsYear(Integer subsYear) throws PersistentException;

    void save(Subscription subscription) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}
