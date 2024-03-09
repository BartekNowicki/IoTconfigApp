package org.myCo.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateRequestDTO {
  private Long id;
  private String deviceIdentifier;
  private String configuration_json_string;
  private LocalDateTime creationDate;
  private LocalDateTime modificationDate;
  private String zoneOffset;
}
