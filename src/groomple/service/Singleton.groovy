package groomple.service

class Singleton {
	private object
	Closure callable
	
	Singleton(Closure callable) {
		this.callable = callable
	}
	
	Object call() {
		if (object.is(null))
			object = callable()
		return object
	}
}
