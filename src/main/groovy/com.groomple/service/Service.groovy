package com.groomple.service

abstract class Service {
  protected Closure callable

  Service(Closure callable) {
    this.callable = callable
  }

  abstract call();
}
