package service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import exception.PersistentException;

public class ServiceInvocationHandlerImpl implements InvocationHandler {
	private static Logger logger = Logger.getLogger(ServiceInvocationHandlerImpl.class);

	private ServiceImpl service;

	public ServiceInvocationHandlerImpl(ServiceImpl service) {
		this.service = service;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
		try {
			Object result = method.invoke(service, arguments);
			service.transaction.commit();
			return result;
		} catch(PersistentException e) {
			rollback(method);
			throw e;
		} catch(InvocationTargetException e) {
			rollback(method);
			throw e.getCause();
		}
	}

	private void rollback(Method method) {
		try {
			service.transaction.rollback();
		} catch(PersistentException e) {
			logger.warn("It is impossible to rollback transaction", e);
		}
	}
}
