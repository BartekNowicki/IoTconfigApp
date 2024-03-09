package org.myCo.repository;

import java.util.Optional;
import org.myCo.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
  Optional<Device> findByDeviceIdentifier(String deviceIdentifier);
}
