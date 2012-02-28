package com.groomple.stuff

class ProtectedStuff extends Stuff {
  ProtectedStuff(Closure callable) {
    super(callable)
  }

  def call() {
    callable
  }
}
