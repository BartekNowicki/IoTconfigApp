package org.myCo.Exception;

public class DeviceNotFoundException extends RuntimeException {
  public DeviceNotFoundException(String deviceIdentifier) {
    super("Device not found with identifier: " + deviceIdentifier);
  }
}
