package com.groomple

import static org.junit.Assert.assertThat
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.Before

class GroompleTests {
	def g

	@Before
	void setUp() {
		g = new Groomple()
	}

	@Test(expected = UnknownServiceException)
	void throws_InvalidParameterException_when_trying_to_get_a_non_existant_property() {
		g.helloWorld
	}

	@Test
	void returns_a_service_with_getService() {
		g.helloWorld = { new HelloWorldService() }
		assertThat g.getService('helloWorld').sayHello(), is('Hello World!')
	}

	@Test
	void returns_a_service_using_array_access() {
		g.helloWorld = { new HelloWorldService() }
		assertThat g['helloWorld'].sayHello(), is('Hello World!')
	}

	@Test
	void returns_a_service_as_if_it_was_a_field() {
		Groomple g = new Groomple()
		g.helloWorld = { new HelloWorldService() }
		assertThat g.helloWorld.sayHello(), is('Hello World!')
	}

	@Test
	void sets_a_service_using_set() {
		g.setService 'helloWorld', { new HelloWorldService() }
		assertThat g.helloWorld.sayHello(), is('Hello World!')
	}

	@Test
	void sets_a_service_using_array_access() {
		Groomple g = new Groomple()
		g['helloWorld'] = { new HelloWorldService() }
		assertThat g.helloWorld.sayHello(), is('Hello World!')
	}

	@Test
	void sets_a_service_as_if_it_was_a_field() {
		g.helloWorld = { new HelloWorldService() }
		assertThat g.helloWorld.sayHello(), is('Hello World!')
	}

	@Test(expected = UnknownServiceException)
	void removes_a_service() {
		g.helloWorld = { new HelloWorldService() }
		g.remove 'helloWorld'
		g.helloWorld
	}

	@Test
	void returns_different_instances_of_a_service_by_default() {
		g.helloWorld = { new HelloWorldService() }
		def helloWorld = g.helloWorld
		def otherHelloWorld = g.helloWorld
		assertThat helloWorld, is(HelloWorldService)
		assertThat helloWorld, is(not(otherHelloWorld))
	}

	@Test
	void can_call_services_as_methods_of_the_container() {
		g.helloWorld = { new HelloWorldService() }
		def helloWorld = g.helloWorld()
		assertThat helloWorld, is(HelloWorldService)
	}

	@Test
	void can_flag_services_as_shared_or_singleton() {
		Groomple g = new Groomple()
		g.share 'helloWorld', { new HelloWorldService() }
		def helloWorld = g.helloWorld
		def sameHelloWorld = g.helloWorld
		assertThat helloWorld, is(HelloWorldService)
		assertThat helloWorld, is(sameHelloWorld)
	}

	@Test
	void can_protect_service_against_execution_while_retrieving_it() {
		g.protect 'helloWorld', { new HelloWorldService() }
		def helloWorld = g.helloWorld
		assertThat helloWorld, is(not(HelloWorldService))
		assertThat helloWorld, is(Closure)
	}

	@Test
	void allows_putAll_operation() {
		g.putAll([helloWorld: { new HelloWorldService() }, helloWorld2: { new HelloWorldService() }])
		assertThat g.helloWorld.sayHello(), is('Hello World!')
		assertThat g.helloWorld2.sayHello(), is('Hello World!')
	}

	@Test
	void allows_leftShift_operation() {
		g << [helloWorld: { new HelloWorldService() }]
		assertThat g.helloWorld.sayHello(), is('Hello World!')
	}
}
