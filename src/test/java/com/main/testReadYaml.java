package com.main;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.main.domain.FileInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class testReadYaml {

    final private ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    @Test
    public void test() throws IOException {
        objectMapper.findAndRegisterModules();
        URL resource = testReadYaml.class.getResource("/configuration.yaml");
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, FileInfo.class);
        List<FileInfo> infos = objectMapper.readValue(resource, javaType);
    }
}
