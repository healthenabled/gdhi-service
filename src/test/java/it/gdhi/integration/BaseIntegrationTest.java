package it.gdhi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BaseIntegrationTest {
    @Autowired
    private DataSource dataSource;
    private ObjectMapper mapper = new ObjectMapper();
    @After
    public void tearDown() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"validated_config.health_indicators");
    }

    ObjectMapper getMapper() {
        return this.mapper;
    }

    String expectedResponseJson(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("responses/" + fileName).getFile())));
    }

}
