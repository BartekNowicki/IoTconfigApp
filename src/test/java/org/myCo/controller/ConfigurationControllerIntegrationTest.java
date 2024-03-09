package org.myCo.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.myCo.DTO.CreateUpdateRequestDTO;
import org.myCo.DTO.CreateUpdateResponseDTO;
import org.myCo.model.Address;
import org.myCo.model.Configuration;
import org.myCo.model.Device;
import org.myCo.repository.ConfigurationRepository;
import org.myCo.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ConfigurationControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private DeviceRepository deviceRepository;

  @Autowired private ConfigurationRepository configurationRepository;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void testDeleteConfiguration_success() throws Exception {

    Device device = new Device();
    String deviceIdentifier = "testDevice";
    device.setDeviceIdentifier(deviceIdentifier);
    Address address =
        new Address("Example Building", "123 Main St", "1", "A", "City", "12345", "Country");
    device.setAddress(address);
    device.setCreationDate(LocalDateTime.now());
    device.setModificationDate(LocalDateTime.now());
    device.setZoneOffset(String.valueOf(ZoneOffset.ofHours(1)));
    deviceRepository.save(device);

    Configuration configuration = new Configuration();
    configuration.setDevice(device);
    configuration.setConfiguration_json_string("{\"key\":\"value\"}");
    configuration.setCreationDate(LocalDateTime.now());
    configuration.setModificationDate(LocalDateTime.now());
    device.setZoneOffset(String.valueOf(ZoneOffset.ofHours(1)));
    configuration.setDeviceIdentifier(deviceIdentifier);
    configuration.setZoneOffset(String.valueOf(ZoneOffset.ofHours(1)));
    Configuration savedConfiguration = configurationRepository.save(configuration);

    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/configurations/{id}", savedConfiguration.getId())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    mockMvc
        .perform(MockMvcRequestBuilders.get("/configurations/{id}", savedConfiguration.getId()))
        .andExpect(status().isNotFound());

    Optional<Device> optionalDevice = deviceRepository.findByDeviceIdentifier(deviceIdentifier);
    assertTrue(optionalDevice.isPresent());
    Device queriedDevice = optionalDevice.get();

    assertAll(
        "Device configurations",
        () -> assertFalse(queriedDevice.getConfigurations().contains(savedConfiguration)),
        () -> assertFalse(configurationRepository.existsById(savedConfiguration.getId())));
  }

  @Test
  @DirtiesContext
  void testAddConfiguration_success() throws Exception {

    Device device = new Device();
    String deviceIdentifier = "testDevice";
    device.setDeviceIdentifier(deviceIdentifier);
    Address address =
        new Address("Example Building", "123 Main St", "1", "A", "City", "12345", "Country");
    device.setAddress(address);
    device.setCreationDate(LocalDateTime.now());
    device.setModificationDate(LocalDateTime.now());
    device.setActivationDate(LocalDateTime.now());
    device.setZoneOffset(String.valueOf(ZoneOffset.ofHours(1)));
    deviceRepository.save(device);

    CreateUpdateRequestDTO requestDTO = new CreateUpdateRequestDTO();
    requestDTO.setDeviceIdentifier(deviceIdentifier);
    requestDTO.setConfiguration_json_string("{\"key\":\"value\"}");
    requestDTO.setCreationDate(LocalDateTime.now());
    requestDTO.setModificationDate(LocalDateTime.now());

    String responseBody =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/configurations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    CreateUpdateResponseDTO responseDTO =
        objectMapper.readValue(responseBody, CreateUpdateResponseDTO.class);
    Long configurationId = responseDTO.getId();

    Optional<Configuration> optionalConfiguration =
        configurationRepository.findById(configurationId);
    assertTrue(optionalConfiguration.isPresent());
    Configuration savedConfiguration = optionalConfiguration.get();

    assertEquals(deviceIdentifier, savedConfiguration.getDeviceIdentifier());
    assertEquals("{\"key\":\"value\"}", savedConfiguration.getConfiguration_json_string());
    assertNotNull(savedConfiguration.getCreationDate());
    assertNotNull(savedConfiguration.getModificationDate());
  }
}
