package org.myCo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  @Autowired private JdbcTemplate jdbcTemplate;

  @Autowired private ResourceLoader resourceLoader;

  @Override
  public void run(String... args) throws Exception {
    executeSqlScript("classpath:device.sql");
    executeSqlScript("classpath:configuration.sql");
  }

  private void executeSqlScript(String path) throws Exception {
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(
                resourceLoader.getResource(path).getInputStream(), StandardCharsets.UTF_8))) {
      String sql = reader.lines().collect(Collectors.joining("\n"));
      jdbcTemplate.execute(sql);
    }
  }
}
