package com.main.store.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.main.domain.FileInfo;
import com.main.domain.enums.EnvironmentVariable;
import com.main.domain.exceptions.NoSuchVariableException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
@PropertySource("classpath:path.properties")
public class StorageBuilder {
    @Value("${file_name}")
    private String fileName;

    @Value("${folder_name}")
    private String folder;

    private Path jbossHomePath;

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private final List<FileInfo> fileInfoList = new ArrayList<>();

    @SneakyThrows
    @PostConstruct
    private void readConfiguration() {
        initFileInfoList();
        initJbossHomePath();
    }

    private void initFileInfoList() throws IOException {
        final String path = Paths.get(folder, fileName).toAbsolutePath().toString();
        log.info("Try to read {}", fileName);
        log.info("Path: {}", path);
        final File resource = new File(path);
        final JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, FileInfo.class);
        fileInfoList.addAll(mapper.readValue(resource, javaType));
        fileInfoList.forEach(file -> log.info(file.getName() + ": {}", file));
    }

    private void initJbossHomePath() throws NoSuchVariableException {
        jbossHomePath = Optional.ofNullable(System.getenv(EnvironmentVariable.JBOSS_HOME.name()))
            .map(Paths::get)
            .orElseThrow(() -> {
                log.error("Environment variable {} does not exist", EnvironmentVariable.JBOSS_HOME.name());
                return new NoSuchVariableException(String.format(
                    "No such variable: %s",
                    EnvironmentVariable.JBOSS_HOME.name()
                ));
            });
    }

    @Bean
    public FileInfoHolder fileInfoHolder() {
        return new FileInfoHolder(fileInfoList);
    }

    @Bean
    public JbossHomeHolder jbossHomeHolder() {
        return new JbossHomeHolder(jbossHomePath);
    }
}
