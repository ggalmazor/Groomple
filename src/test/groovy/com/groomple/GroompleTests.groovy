package com.groomple

import org.junit.Before
import org.junit.Test
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.assertThat

class GroompleTests {
  def groomple

  @Before
  void setUp() {
    groomple = new Groomple()
  }

  @Test(expected = UnknownServiceException)
  void throws_InvalidParameterException_when_trying_to_get_a_non_existant_property() {
    groomple.helloWorld
  }

  @Test
  void returns_a_service_with_getService() {
    groomple.helloWorld = { new HelloWorldService() }
    assertThat groomple.getService('helloWorld').sayHello(), is('Hello World!')
  }

  @Test
  void returns_a_service_using_array_access() {
    groomple.helloWorld = { new HelloWorldService() }
    assertThat groomple['helloWorld'].sayHello(), is('Hello World!')
  }

  @Test
  void returns_a_service_as_if_it_was_a_field() {
    Groomple g = new Groomple()
    g.helloWorld = { new HelloWorldService() }
    assertThat g.helloWorld.sayHello(), is('Hello World!')
  }

  @Test
  void sets_a_service_using_set() {
    groomple.setService 'helloWorld', { new HelloWorldService() }
    assertThat groomple.helloWorld.sayHello(), is('Hello World!')
  }

  @Test
  void sets_a_service_using_array_access() {
    Groomple g = new Groomple()
    g['helloWorld'] = { new HelloWorldService() }
    assertThat g.helloWorld.sayHello(), is('Hello World!')
  }

  @Test
  void sets_a_service_as_if_it_was_a_field() {
    groomple.helloWorld = { new HelloWorldService() }
    assertThat groomple.helloWorld.sayHello(), is('Hello World!')
  }

  @Test(expected = UnknownServiceException)
  void removes_a_service() {
    groomple.helloWorld = { new HelloWorldService() }
    groomple.remove 'helloWorld'
    groomple.helloWorld
  }

  @Test
  void returns_different_instances_of_a_service_by_default() {
    groomple.helloWorld = { new HelloWorldService() }
    def helloWorld = groomple.helloWorld
    def otherHelloWorld = groomple.helloWorld
    assertThat helloWorld, is(HelloWorldService)
    assertThat helloWorld, is(not(otherHelloWorld))
  }

  @Test
  void can_call_services_as_methods_of_the_container() {
    groomple.helloWorld = { new HelloWorldService() }
    def helloWorld = groomple.helloWorld()
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
    groomple.protect 'helloWorld', { new HelloWorldService() }
    def helloWorld = groomple.helloWorld
    assertThat helloWorld, is(not(HelloWorldService))
    assertThat helloWorld, is(Closure)
  }

  @Test
  void allows_putAll_operation() {
    groomple.putAll([helloWorld: { new HelloWorldService() }, helloWorld2: { new HelloWorldService() }])
    assertThat groomple.helloWorld.sayHello(), is('Hello World!')
    assertThat groomple.helloWorld2.sayHello(), is('Hello World!')
  }

  @Test
  void allows_leftShift_operation() {
    groomple << [helloWorld: { new HelloWorldService() }]
    assertThat groomple.helloWorld.sayHello(), is('Hello World!')
  }
}
