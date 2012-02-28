package com.groomple.stuff

abstract class Stuff {
  protected Closure callable

  Stuff(Closure callable) {
    this.callable = callable
  }

  abstract call();
}
