package com.groomple.stuff

class SharedStuff extends Stuff {
  private singleton

  SharedStuff(Closure callable) {
    super(callable)
  }

  def call() {
    if (null == singleton)
      singleton = callable()
    singleton
  }
}
