package org.myCo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Device extends BaseEntity {

  @Embedded private Address address;

  private LocalDateTime activationDate;

  private LocalDateTime deactivationDate;

  @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Configuration> configurations;

  public void addConfiguration(Configuration configuration) {
    configurations.add(configuration);
    configuration.setDevice(this);
  }

  public void removeConfiguration(Configuration configuration) {
    configurations.remove(configuration);
    configuration.setDevice(null);
  }
}
