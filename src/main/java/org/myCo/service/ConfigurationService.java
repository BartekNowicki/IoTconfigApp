package org.myCo.service;

import java.util.List;
import org.myCo.DTO.CreateUpdateRequestDTO;
import org.myCo.DTO.CreateUpdateResponseDTO;
import org.myCo.DTO.ReadResponseDTO;

public interface ConfigurationService {
  CreateUpdateResponseDTO createConfiguration(CreateUpdateRequestDTO createUpdateRequestDTO);

  ReadResponseDTO readConfiguration(Long id);

  List<ReadResponseDTO> readAllConfigurations();

  CreateUpdateResponseDTO updateConfiguration(CreateUpdateRequestDTO createUpdateRequestDTO);

  void deleteConfiguration(Long id);
}
