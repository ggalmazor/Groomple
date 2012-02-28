package com.groomple

import com.groomple.service.ProtectedService
import com.groomple.service.SharedService

class Groomple {
  @Delegate Map services = [:]
  
  def getService(String name) {
    if (!contains(name))
      throw new UnknownServiceException("Service ${name} is not defined")
    isCallable(services[name]) ? services[name].call() : services[name]
  }

  void setService(String name, value) {
    services[name] = value
  }

  def getProperty(String name) {
    getService name
  }

  void setProperty(String name, value) {
    setService name, value
  }

  void leftShift(services) {
    putAll(services)
  }

  void putAll(services) {
    services.each {
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

  private boolean isCallable(object) {
    object instanceof Closure || object instanceof SharedService || object instanceof ProtectedService
  }

  private boolean contains(String stuff) {
    services.containsKey stuff
  }
}