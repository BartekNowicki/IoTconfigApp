package org.myCo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateResponseDTO {
  private Long id;
  private String deviceIdentifier;
  private String configuration_json_string;
  private String creationDate;
  private String modificationDate;
  private String zoneOffset;
}
