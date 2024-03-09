package org.myCo.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.myCo.DTO.CreateUpdateRequestDTO;
import org.myCo.DTO.CreateUpdateResponseDTO;
import org.myCo.DTO.ReadResponseDTO;
import org.myCo.model.Configuration;

public class ConfigurationMapper {

  public static CreateUpdateResponseDTO toCreateUpdateResponseDTO(Configuration configuration) {
    CreateUpdateResponseDTO dto = new CreateUpdateResponseDTO();
    dto.setId(configuration.getId());
    dto.setDeviceIdentifier(configuration.getDeviceIdentifier());
    dto.setConfiguration_json_string(configuration.getConfiguration_json_string());
    dto.setCreationDate(
        getFormattedDateWithZoneOffset(configuration, configuration.getCreationDate()));
    dto.setModificationDate(
        getFormattedDateWithZoneOffset(configuration, configuration.getModificationDate()));
    dto.setZoneOffset(configuration.getZoneOffset());
    return dto;
  }

  public static ReadResponseDTO toReadResponseDTO(Configuration configuration) {
    ReadResponseDTO dto = new ReadResponseDTO();
    dto.setId(configuration.getId());
    dto.setConfiguration(configuration.getConfiguration_json_string());
    String formattedDate =
        getFormattedDateWithZoneOffset(configuration, configuration.getCreationDate());
    dto.setCreationDate(formattedDate);
    return dto;
  }

  private static String getFormattedDateWithZoneOffset(
      Configuration configuration, LocalDateTime dateTime) {
    ZoneOffset zoneOffset = ZoneOffset.of(configuration.getZoneOffset());
    ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, zoneOffset);
    ZoneId userTimeZone = ZoneId.systemDefault();
    ZonedDateTime userZonedDateTime = zonedDateTime.withZoneSameInstant(userTimeZone);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
    String formattedDate = userZonedDateTime.format(formatter);
    return formattedDate;
  }

  public static Configuration fromDTO(CreateUpdateRequestDTO dto) {
    Configuration configuration = new Configuration();

    if (dto.getId() != null) {
      configuration.setId(dto.getId());
    }

    configuration.setDeviceIdentifier(dto.getDeviceIdentifier());
    configuration.setConfiguration_json_string(dto.getConfiguration_json_string());
    configuration.setCreationDate(dto.getCreationDate());
    configuration.setModificationDate(dto.getModificationDate());
    configuration.setZoneOffset(dto.getZoneOffset());

    return configuration;
  }
}
