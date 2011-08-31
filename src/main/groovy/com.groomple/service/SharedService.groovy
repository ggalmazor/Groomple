package com.groomple.service

class SharedService {
	def singleton
	Closure callable

	SharedService(Closure callable) {
		this.callable = callable
	}
	
	Object call() {
		if (null == singleton)
			singleton = callable()
		return singleton
	}
}
