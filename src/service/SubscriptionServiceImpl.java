package service;

import java.util.List;
import dao.SubscriptionDao;
import dao.mysql.SubscriptionDaoImpl;
import domain.Subscription;
import exception.PersistentException;


public class SubscriptionServiceImpl implements SubscriptionService {

    @Override
    public List<Subscription> findAll() throws PersistentException {
        SubscriptionDao dao = new SubscriptionDaoImpl();
        return dao.read();
    }

    @Override
    public Subscription findById(Integer id) throws PersistentException {
        SubscriptionDao dao = new SubscriptionDaoImpl();
        return dao.read(id);
    }

    @Override
    public List<Subscription> findByUserId(Integer userId) throws PersistentException {
        SubscriptionDao dao = new SubscriptionDaoImpl();
        return dao.readByUserId(userId);
    }

    @Override
    public Subscription findByPublicationId(Integer publicationId) throws PersistentException {
        SubscriptionDao dao = new SubscriptionDaoImpl();
        return dao.readByPublicationId(publicationId);
    }

    @Override
    public Subscription findBySubsYear(Integer subsYear) throws PersistentException {
        SubscriptionDao dao = new SubscriptionDaoImpl();
        return dao.readBySubsYear(subsYear);
    }

    @Override
    public void save(Subscription subscription) throws PersistentException {
        SubscriptionDao dao = new SubscriptionDaoImpl();
        if (subscription.getId() != null) {
            dao.update(subscription);
        } else {
            subscription.setId(dao.create(subscription));
        }
    }

    @Override
    public void delete(Integer id) throws PersistentException {
        SubscriptionDao dao = new SubscriptionDaoImpl();
        dao.delete(id);
    }
}
