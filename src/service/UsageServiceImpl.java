package service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.UsageDao;
import domain.Usage;
import exception.PersistentException;

public class UsageServiceImpl extends ServiceImpl implements UsageService {
	@Override
	public Usage findByIdentity(Integer identity) throws PersistentException {
		UsageDao usageDao = transaction.createDao(UsageDao.class);
		return usageDao.read(identity);
	}

	@Override
	public boolean save(Usage usage) throws PersistentException {
		UsageDao usageDao = transaction.createDao(UsageDao.class);
		Date now = new Date();
		if(usage.getIdentity() != null) {
			usage.setReturnDate(now);
			usageDao.update(usage);
		} else {
			List<Usage> usages;
			usages = usageDao.readByBook(usage.getBook().getIdentity());
			for(Usage u : usages) {
				if(u.getReturnDate() == null) {
					return false;
				}
			}
			usages = usageDao.readByReader(usage.getReader().getIdentity());
			for(Usage u : usages) {
				if(u.getReturnDate() == null && u.getPlanReturnDate().before(now)) {
					return false;
				}
			}
			usage.setDeliveryDate(now);
			usage.setReturnDate(null);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.MONTH, 1);
			usage.setPlanReturnDate(calendar.getTime());
			usage.setIdentity(usageDao.create(usage));
		}
		return true;
	}
}
