package com.groomple

import static org.junit.Assert.assertThat
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import java.security.InvalidParameterException
import org.junit.Test

class GroompleTests {
	
	@Test(expected = InvalidParameterException)
	void 'throws InvalidParameterException when trying to get a non existant property'() {
		Groomple g = new Groomple()
		g.helloWorld
	}

	@Test
	void 'returns a service with get'() {
		Groomple g = new Groomple()
		g.helloWorld = { new HelloWorldService() }
		assertThat g.get('helloWorld').sayHello(), is('Hello World!')
	}

	@Test
	void 'returns a service using array access'() {
		Groomple g = new Groomple()
		g.helloWorld = { new HelloWorldService() }
		assertThat g['helloWorld'].sayHello(), is('Hello World!')
	}

	@Test
	void 'returns a service as if it was a field'() {
		Groomple g = new Groomple()
		g.helloWorld = { new HelloWorldService() }
		assertThat g.helloWorld.sayHello(), is('Hello World!')
	}

	@Test
	void 'sets a service using set'() {
		Groomple g = new Groomple()
		g.set 'helloWorld', { new HelloWorldService() }
		assertThat g.helloWorld.sayHello(), is('Hello World!')
	}

	@Test
	void 'sets a service using array access'() {
		Groomple g = new Groomple()
		g['helloWorld'] = { new HelloWorldService() }
		assertThat g.helloWorld.sayHello(), is('Hello World!')
	}

	@Test
	void 'sets a service as if it was a field'() {
		Groomple g = new Groomple()
		g.helloWorld = { new HelloWorldService() }
		assertThat g.helloWorld.sayHello(), is('Hello World!')
	}

	@Test(expected = InvalidParameterException)
	void 'removes a service'() {
		Groomple g = new Groomple()
		g.helloWorld = { new HelloWorldService() }
		g.remove 'helloWorld'
		g.helloWorld
	}

	@Test
	void 'returns different instances of a service by default'() {
		Groomple g = new Groomple()
		g.helloWorld = { new HelloWorldService() }
		def helloWorld = g.helloWorld
		def otherHelloWorld = g.helloWorld
		assertThat helloWorld, is(HelloWorldService)
		assertThat helloWorld, is(not(otherHelloWorld))
	}
	
	@Test
	void 'can call services as methods of the container'() {
		Groomple g = new Groomple()
		g.helloWorld = { new HelloWorldService() }
		def helloWorld = g.helloWorld()
		assertThat helloWorld, is(HelloWorldService)
	}

	@Test
	void 'can flag services as shared or singleton'() {
		Groomple g = new Groomple()
		g.share 'helloWorld', { new HelloWorldService() }
		def helloWorld = g.helloWorld
		def sameHelloWorld = g.helloWorld
		assertThat helloWorld, is(HelloWorldService)
		assertThat helloWorld, is(sameHelloWorld)
	}
	
	@Test
	void 'can protect service against execution while retrieving it'() {
		Groomple g = new Groomple()
		g.protect 'helloWorld', { new HelloWorldService() }
		def helloWorld = g.helloWorld
		assertThat helloWorld, is(not(HelloWorldService))
		assertThat helloWorld, is(Closure)
	}
	
}
