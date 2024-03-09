package org.myCo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.myCo.DTO.CreateUpdateRequestDTO;
import org.myCo.DTO.CreateUpdateResponseDTO;
import org.myCo.DTO.ReadResponseDTO;
import org.myCo.Exception.ConfigurationNotFoundException;
import org.myCo.Exception.DeviceNotFoundException;
import org.myCo.model.Configuration;
import org.myCo.model.Device;
import org.myCo.repository.ConfigurationRepository;
import org.myCo.repository.DeviceRepository;
import org.myCo.utils.ConfigurationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

  @Autowired private ConfigurationRepository configurationRepository;
  @Autowired private DeviceRepository deviceRepository;

  @Override
  @Transactional
  public CreateUpdateResponseDTO createConfiguration(CreateUpdateRequestDTO createRequestDTO) {

    Device device =
        deviceRepository
            .findByDeviceIdentifier(createRequestDTO.getDeviceIdentifier())
            .orElseThrow(() -> new DeviceNotFoundException(createRequestDTO.getDeviceIdentifier()));

    Configuration configuration = ConfigurationMapper.fromDTO(createRequestDTO);
    configuration.setDevice(device);

    Configuration saved = configurationRepository.save(configuration);
    saved.getDevice().addConfiguration(saved);
    return ConfigurationMapper.toCreateUpdateResponseDTO(saved);
  }

  @Override
  public ReadResponseDTO readConfiguration(Long id) {
    Configuration configuration =
        configurationRepository
            .findById(id)
            .orElseThrow(() -> new ConfigurationNotFoundException(id));
    return ConfigurationMapper.toReadResponseDTO(configuration);
  }

  @Override
  public List<ReadResponseDTO> readAllConfigurations() {
    return configurationRepository.findAll().stream()
        .map(ConfigurationMapper::toReadResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public CreateUpdateResponseDTO updateConfiguration(CreateUpdateRequestDTO updateRequestDTO) {

    Configuration updatedConfiguration =
        configurationRepository
            .findById(updateRequestDTO.getId())
            .map(
                configuration -> {
                  configuration.setConfiguration_json_string(
                      updateRequestDTO.getConfiguration_json_string());
                  Configuration saved = configurationRepository.save(configuration);
                  saved.getDevice().addConfiguration(saved);
                  return saved;
                })
            .orElseThrow(() -> new ConfigurationNotFoundException(updateRequestDTO.getId()));
    return ConfigurationMapper.toCreateUpdateResponseDTO(updatedConfiguration);
  }

  @Override
  @Transactional
  public void deleteConfiguration(Long id) {
    Configuration configuration =
        configurationRepository
            .findById(id)
            .orElseThrow(() -> new ConfigurationNotFoundException(id));
    configuration.getDevice().removeConfiguration(configuration);
    configurationRepository.delete(configuration);
  }
}
