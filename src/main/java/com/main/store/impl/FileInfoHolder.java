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
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Repository
public class FileInfoHolder implements Storage<FileInfo> {

    private static final String FILE_NAME = "configuration.yaml";

    private String path;

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private List<FileInfo> fileInfoList;

    @SneakyThrows
    @PostConstruct
    private void readConfiguration() {
        initializePath();
        log.info("Try to read {}", FILE_NAME);
        log.info("Path: {}", path);
        File resource = new File(path);
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, FileInfo.class);
        fileInfoList = new ArrayList<>();
        fileInfoList = mapper.readValue(resource, javaType);
        log.info(FILE_NAME + ": {}", fileInfoList);
    }

    private void initializePath() {
        String sourceCodePath = Paths.get("").toAbsolutePath().toString();
        StringJoiner joiner = new StringJoiner(FileSystems.getDefault().getSeparator());
        joiner.add(sourceCodePath);
        joiner.add(FILE_NAME);
        path = joiner.toString();
    }

    @Override
    public List<FileInfo> getAll() {
        return fileInfoList;
    }
}
