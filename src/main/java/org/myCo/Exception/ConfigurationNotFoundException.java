package org.myCo.Exception;

public class ConfigurationNotFoundException extends RuntimeException {
  public ConfigurationNotFoundException(Long id) {
    super("Configuration not found with ID: " + id);
  }
}
