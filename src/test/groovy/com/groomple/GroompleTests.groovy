package com.groomple

import org.junit.Before
import org.junit.Test

class GroompleTests {
  Groomple groomple

  @Before
  void setUp() {
    groomple = new Groomple()
  }

  @Test(expected = UnknownStuffException)
  void throws_InvalidParameterException_when_trying_to_get_a_non_existant_property() {
    groomple.someStuff
  }

  @Test
  void returns_stuff_with_getService() {
    groomple.someStuff = { new SomeStuff() }
    assert groomple.get('someStuff').foo() == SomeStuff.BAR
  }

  @Test
  void returns_stuff_using_array_access() {
    groomple.someStuff = { new SomeStuff() }
    assert groomple['someStuff'].foo() == SomeStuff.BAR
  }

  @Test
  void returns_stuff_as_if_it_was_a_field() {
    Groomple g = new Groomple()
    g.someStuff = { new SomeStuff() }
    assert g.someStuff.foo() == SomeStuff.BAR
  }

  @Test
  void sets_stuff_using_set() {
    groomple.set 'someStuff', { new SomeStuff() }
    assert groomple.someStuff.foo() == SomeStuff.BAR
  }

  @Test
  void sets_stuff_using_array_access() {
    Groomple g = new Groomple()
    g['someStuff'] = { new SomeStuff() }
    assert g.someStuff.foo() == SomeStuff.BAR
  }

  @Test
  void sets_stuff_as_if_it_was_a_field() {
    groomple.someStuff = { new SomeStuff() }
    assert groomple.someStuff.foo() == SomeStuff.BAR
  }

  @Test(expected = UnknownStuffException)
  void removes_stuff() {
    groomple.someStuff = { new SomeStuff() }
    groomple.remove 'someStuff'
    groomple.someStuff
  }

  @Test
  void returns_different_instances_of_stuff_by_default() {
    groomple.someStuff = { new SomeStuff() }
    def someStuff = groomple.someStuff
    def otherHelloWorld = groomple.someStuff
    assert someStuff instanceof SomeStuff
    assert someStuff != otherHelloWorld
  }

  @Test
  void can_call_stuff_as_methods_of_the_container() {
    groomple.someStuff = { new SomeStuff() }
    def someStuff = groomple.someStuff()
    assert someStuff instanceof SomeStuff
  }

  @Test
  void can_flag_stuff_as_shared_or_singleton() {
    Groomple g = new Groomple()
    g.share 'someStuff', { new SomeStuff() }
    def someStuff = g.someStuff
    def sameHelloWorld = g.someStuff
    assert someStuff instanceof SomeStuff
    assert someStuff == sameHelloWorld
  }

  @Test
  void can_protect_stuff_against_execution_while_retrieving_it() {
    groomple.protect 'someStuff', { new SomeStuff() }
    def someStuff = groomple.someStuff
    assert !(someStuff instanceof SomeStuff)
    assert someStuff instanceof Closure
  }

  @Test
  void allows_putAll_operation() {
    groomple.putAll([someStuff: { new SomeStuff() }, someStuff2: { new SomeStuff() }])
    assert groomple.someStuff.foo() == SomeStuff.BAR
    assert groomple.someStuff2.foo() == SomeStuff.BAR
  }

  @Test
  void allows_leftShift_operation() {
    groomple << [someStuff: { new SomeStuff() }]
    assert groomple.someStuff.foo() == SomeStuff.BAR
  }

  private class SomeStuff {
    static BAR = 'bar'
    def foo() {
      BAR
    }
  }
}
