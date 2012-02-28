package com.groomple.service

class SharedService extends Service {
  private singleton

  SharedService(Closure callable) {
    super(callable)
  }

  def call() {
    if (null == singleton)
      singleton = callable()
    singleton
  }
}
