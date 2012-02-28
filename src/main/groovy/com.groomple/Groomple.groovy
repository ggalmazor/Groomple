package com.groomple

import com.groomple.stuff.ProtectedStuff
import com.groomple.stuff.SharedStuff

class Groomple {
  @Delegate Map bag = [:]

  def get(String name) {
    if (!contains(name))
      throw new UnknownStuffException("Stuff ${name} is not in the bag")
    isCallable(bag[name]) ? bag[name].call() : bag[name]
  }

  void set(String name, value) {
    bag[name] = value
  }

  def getProperty(String name) {
    get name
  }

  void setProperty(String name, value) {
    set name, value
  }

  void leftShift(Map stuff) {
    putAll(stuff)
  }

  void putAll(Map<String, Object> stuff) {
    stuff.each {
      set it.key, it.value
    }
  }

  def invokeMethod(String name, args) {
    return get(name)
  }

  void remove(String stuff) {
    bag.remove stuff
  }

  void share(String stuff, Closure callable) {
    setProperty(stuff, new SharedStuff(callable))
  }

  void protect(String stuff, Closure callable) {
    setProperty(stuff, new ProtectedStuff(callable))
  }

  private boolean isCallable(object) {
    object instanceof Closure || object instanceof SharedStuff || object instanceof ProtectedStuff
  }

  private boolean contains(String stuff) {
    bag.containsKey stuff
  }
}