package service;

import domain.Usage;
import exception.PersistentException;

public interface UsageService extends Service {
	Usage findByIdentity(Integer identity) throws PersistentException;

	boolean save(Usage usage) throws PersistentException;
}
