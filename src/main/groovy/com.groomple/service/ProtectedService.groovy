package com.groomple.service

class ProtectedService {
	Closure callable

	ProtectedService(Closure callable) {
		this.callable = callable
	}
	
	Object call() {
		return callable
	}
}
