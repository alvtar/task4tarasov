package service;

import java.sql.SQLException;
import exception.PersistentException;

public interface ServiceRegistrator {
    abstract void register() throws PersistentException, SQLException;

}
