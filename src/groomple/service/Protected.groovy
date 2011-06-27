package groomple.service

class Protected {
	Closure callable
	
	Protected(Closure callable) {
		this.callable = callable
	}
	
	Object call() {
		return callable
	}
}
