package com.main.store.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.main.domain.FileInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@PropertySource("classpath:path.properties")
public class StorageBuilder {
    @Value("${file_name}")
    private String fileName;

    @Value("${folder_name}")
    private String folder;

    private String path;

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private List<FileInfo> fileInfoList;

    @SneakyThrows
    @PostConstruct
    private void readConfiguration() {
        path = Paths.get(folder, fileName).toAbsolutePath().toString();
        log.info("Try to read {}", fileName);
        log.info("Path: {}", path);
        final File resource = new File(path);
        final JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, FileInfo.class);
        fileInfoList = new ArrayList<>();
        fileInfoList = mapper.readValue(resource, javaType);
        log.info(fileName + ": {}", fileInfoList);
    }

    @Bean
    public FileInfoHolder fileInfoHolder() {
        return new FileInfoHolder(fileInfoList);
    }
}
