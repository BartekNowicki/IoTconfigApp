package org.myCo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadResponseDTO {
  private Long id;
  private String configuration;
  private String creationDate;
}
