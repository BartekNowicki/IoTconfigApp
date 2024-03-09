package org.myCo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

  @Column(length = 128)
  private String name;

  @Column(nullable = false, length = 128)
  private String street;

  @Column(name = "building_number", nullable = false, length = 128)
  private String buildingNumber;

  @Column(name = "apartment_number", length = 128)
  private String apartmentNumber;

  @Column(nullable = false, length = 128)
  private String city;

  @Column(name = "postal_code", nullable = false, length = 128)
  private String postalCode;

  @Column(nullable = false, length = 128)
  private String country;
}
