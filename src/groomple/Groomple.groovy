package groomple

import groomple.service.*

import java.security.InvalidParameterException

class Groomple implements Iterable {
	Map services = [:]

	public Iterator iterator() {
		return services.iterator()
	}

	def getProperty(String name) {
		if (!services.containsKey(name))
			throw new InvalidParameterException("Service ${name} is not defined")
		return isCallable(services[name]) ? services[name]() : services[name]
	}

	void setProperty(String name, value) {
		services[name] = value
	}

	def invokeMethod(String name, args) {
		return getProperty(name)
	}
	
	public void remove(String service) {
		services.remove(service)
	}

	public void share(String service, Closure callable) {
		setProperty(service, new Singleton(callable))
	}

	public void protect(String service, Closure callable) {
		setProperty(service, new Protected(callable))
	}

	private isCallable(Object object) {
		return object instanceof Closure || object instanceof Singleton || object instanceof Protected
	}
}