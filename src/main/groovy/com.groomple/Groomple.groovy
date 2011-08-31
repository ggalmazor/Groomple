package com.groomple

import com.groomple.service.*
import java.security.InvalidParameterException

class Groomple implements Iterable {
	Map services = [:]

	Iterator iterator() {
		return services.iterator()
	}

	def get(String name) {
		if (!services.containsKey(name))
			throw new InvalidParameterException("Service ${name} is not defined")
		return isCallable(services[name]) ? services[name]() : services[name]
	}

	def getProperty(String name) {
		get name
	}

	void set(String name, value) {
		services[name] = value
	}

	void setProperty(String name, value) {
		set name, value
	}

	def invokeMethod(String name, args) {
		return get(name)
	}
	
	void remove(String service) {
		services.remove service
	}

	void share(String service, Closure callable) {
		setProperty(service, new SharedService(callable))
	}

	void protect(String service, Closure callable) {
		setProperty(service, new ProtectedService(callable))
	}

	private isCallable(Object object) {
		return object instanceof Closure || object instanceof SharedService || object instanceof ProtectedService
	}
}