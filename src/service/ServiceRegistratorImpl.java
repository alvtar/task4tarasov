package service;

import java.sql.SQLException;

import dao.pool.ConnectionPool;
import exception.PersistentException;



public class ServiceRegistratorImpl implements ServiceRegistrator {
    
    public void register() throws PersistentException, SQLException {
        
        ConnectionPool pool=new ConnectionPool();
        pool.init();   
        ServiceLocator locator = new ServiceLocator();
    
        UserService user=new UserServiceImpl();
        locator.registerService(UserService.class,user);
        
        PublicationService publication=new PublicationServiceImpl();
        locator.registerService(PublicationService.class, publication);
        
        SubscriptionService subscription=new SubscriptionServiceImpl();
        locator.registerService(SubscriptionService.class, subscription);
        
        ServiceLocator.setLocator(locator);
    }
    
    public ServiceRegistratorImpl() throws PersistentException, SQLException {
        register();
    }
}
