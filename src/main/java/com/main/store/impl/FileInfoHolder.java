package com.main.store.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.main.domain.FileInfo;
import com.main.store.Storage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class FileInfoHolder implements Storage<FileInfo> {

    private static final String path = "/template/configuration.yaml" ;
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private List<FileInfo> fileInfoList;

    @SneakyThrows
    @PostConstruct
    private void readConfiguration() {
        log.info("Try to read configuration.yaml");
        URL resource = FileInfoHolder.class.getResource(path);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, FileInfo.class);
        fileInfoList = new ArrayList<>();
        fileInfoList = mapper.readValue(resource, javaType);
        log.info("configuration.yaml: {}", fileInfoList);
    }

    @Override
    public List<FileInfo> getAll() {
        return fileInfoList;
    }
}
