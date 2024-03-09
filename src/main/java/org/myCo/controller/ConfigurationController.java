package org.myCo.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import org.myCo.DTO.CreateUpdateRequestDTO;
import org.myCo.DTO.CreateUpdateResponseDTO;
import org.myCo.DTO.ReadResponseDTO;
import org.myCo.Exception.BadRequestException;
import org.myCo.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configurations")
public class ConfigurationController {

  @Autowired private ConfigurationService configurationService;

  @PostMapping
  public ResponseEntity<CreateUpdateResponseDTO> createConfiguration(
      @RequestBody CreateUpdateRequestDTO createRequestDTO) throws BadRequestException {

    if (createRequestDTO.getId() != null) {
      throw new BadRequestException(
              "Do not use POST for updating configurations. Use PATCH instead");
    }

    createRequestDTO.setZoneOffset(computeZoneOffset());
    createRequestDTO.setCreationDate(LocalDateTime.now());
    createRequestDTO.setModificationDate(LocalDateTime.now());

    CreateUpdateResponseDTO created = configurationService.createConfiguration(createRequestDTO);
    return ResponseEntity.ok(created);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReadResponseDTO> readConfiguration(@PathVariable Long id) {
    ReadResponseDTO configuration = configurationService.readConfiguration(id);
    return ResponseEntity.ok(configuration);
  }

  @GetMapping
  public ResponseEntity<List<ReadResponseDTO>> readAllConfigurations() {
    List<ReadResponseDTO> configurations = configurationService.readAllConfigurations();
    return ResponseEntity.ok(configurations);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<CreateUpdateResponseDTO> updateConfiguration(
      @PathVariable Long id, @RequestBody CreateUpdateRequestDTO updateRequestDTO) {

    if (updateRequestDTO.getId() == null) {
      throw new BadRequestException(
              "Missing resource identifier. The 'id' must be provided in the request body for update operations");
    }

    if (!id.equals(updateRequestDTO.getId())) {
      throw new BadRequestException(
              "Mismatch between resource identifiers. The 'id' in the path variable does not match the 'id' provided in the request body");
    }

    updateRequestDTO.setZoneOffset(computeZoneOffset());
    updateRequestDTO.setModificationDate(LocalDateTime.now());

    CreateUpdateResponseDTO updated = configurationService.updateConfiguration(updateRequestDTO);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteConfiguration(@PathVariable Long id) {
    configurationService.deleteConfiguration(id);
    return ResponseEntity.ok().build();
  }

  private String computeZoneOffset() {
    ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now());
    return zoneOffset.toString();
  }
}
