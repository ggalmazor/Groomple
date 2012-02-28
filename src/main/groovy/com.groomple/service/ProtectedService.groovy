package com.groomple.service

class ProtectedService extends Service {
  ProtectedService(Closure callable) {
    super(callable)
  }

  def call() {
    callable
  }
}
