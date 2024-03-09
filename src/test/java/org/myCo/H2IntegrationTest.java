package org.myCo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.myCo.model.Address;
import org.myCo.model.Device;
import org.myCo.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class H2IntegrationTest {

  @Autowired private DeviceRepository deviceRepository;

  @Test
  void testDeviceRepository() {
    // Given
    Device device = new Device();
    device.setDeviceIdentifier("testDevice");
    Address address =
        new Address("Example Building", "123 Main St", "1", "A", "City", "12345", "Country");

    device.setAddress(address);
    device.setCreationDate(LocalDateTime.now());
    device.setModificationDate(LocalDateTime.now());
    device.setZoneOffset(String.valueOf(ZoneOffset.ofHours(1)));

    deviceRepository.save(device);

    // When
    List<Device> retrievedDevices = deviceRepository.findAll();

    // Then
    assertThat(retrievedDevices).isNotEmpty();
    assertThat(retrievedDevices).hasSize(1);
    assertThat(retrievedDevices.get(0).getDeviceIdentifier()).isEqualTo("testDevice");
    assertThat(retrievedDevices.get(0).getAddress()).isEqualTo(address);
  }
}
