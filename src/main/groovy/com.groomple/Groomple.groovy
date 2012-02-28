package com.groomple

import com.groomple.service.ProtectedService
import com.groomple.service.SharedService

class Groomple {
  @Delegate Map services = [:]

  def getService(String name) {
    if (!services.containsKey(name))
      throw new UnknownServiceException("Service ${name} is not defined")
    isCallable(services[name]) ? services[name].call() : services[name]
  }

  // Groovy needs this method to be able to manage
  // services as if they were object fields
  // (aka return container.service)
  def getProperty(String name) {
    getService name
  }

  void setService(String name, value) {
    services[name] = value
  }

  // Groovy needs this method to be able to manage
  // services as if they were object fields
  // (aka container.service = something)
  void setProperty(String name, value) {
    setService name, value
  }

  void leftShift(serviceCollection) {
    putAll(serviceCollection)
  }

  void putAll(serviceCollection) {
    serviceCollection.each {
      setService it.key, it.value
    }
  }

  def invokeMethod(String name, args) {
    return getService(name)
  }

  void remove(String service) {
    services.remove service
  }

  void share(String service, Closure callable) {
    setProperty(service, new SharedService(callable))
  }

  void protect(String service, Closure callable) {
    setProperty(service, new ProtectedService(callable))
  }

  private isCallable(Object object) {
    return object instanceof Closure || object instanceof SharedService || object instanceof ProtectedService
  }
}