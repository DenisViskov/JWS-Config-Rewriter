package com.main.store.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.main.domain.FileInfo;
import com.main.domain.UnitValues;
import com.main.domain.enums.EnvironmentVariable;
import com.main.domain.enums.PatternSymbol;
import com.main.domain.exceptions.NoSuchVariableException;
import com.main.service.conditions.EnvironmentCheck;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    @Autowired
    private EnvironmentCheck<Boolean, EnvironmentVariable> environmentChecker;

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
        if (!environmentChecker.checkEnvironmentVariables(Collections.singletonList(EnvironmentVariable.JBOSS_HOME))) {
            log.error("Environment variable {} does not exist", EnvironmentVariable.JBOSS_HOME.name());
            throw new NoSuchVariableException(String.format(
                "No such variable: %s",
                EnvironmentVariable.JBOSS_HOME.name()
            ));
        }
        jbossHomePath = Paths.get(System.getenv(EnvironmentVariable.JBOSS_HOME.name()));
    }

    @Bean
    public FileInfoHolder fileInfoHolder() {
        return new FileInfoHolder(fileInfoList);
    }

    @Bean
    public JbossHomeHolder jbossHomeHolder() {
        return new JbossHomeHolder(jbossHomePath);
    }

    @Bean
    public ParsedFileInfoHolder parsedFileInfoHolder() {
        List<FileInfo> fileInfoList = this.fileInfoList.stream()
            .peek(this::replacePatternOnValueIfPresent)
            .collect(
                Collectors.toList());
        return null;
    }

    @SneakyThrows private void replacePatternOnValueIfPresent(FileInfo file) {
        final boolean hasPattern = file.getName().contains(PatternSymbol.SPECIAL_SYMBOL.getSymbol()) ||
                                   file.getValues().stream().anyMatch(unit -> unit.getOldValue()
                                       .contains(PatternSymbol.SPECIAL_SYMBOL.getSymbol()));
        if (!hasPattern) {
            return;
        }

        if (file.getName().contains(PatternSymbol.SPECIAL_SYMBOL.getSymbol())) {
            replacePatternName(file);
        }
        replaceOldValueUnitPattern(file);
    }

    @SneakyThrows private void replacePatternName(FileInfo file) {
        final Pattern pattern = Pattern.compile(
            file.getName().replaceAll(PatternSymbol.SPECIAL_SYMBOL.getSymbol(), ".")
        );

        final Path source = Paths.get(jbossHomePath.toAbsolutePath().toString(), file.getPath()).normalize();
        Path found = Files.list(source)
            .filter(path -> pattern.matcher(path.getFileName().toString()).matches())
            .findFirst()
            .orElseThrow(() -> {
                log.error("File with pattern: [{}] not found", pattern.pattern());
                return new FileNotFoundException(String.format("File [%s] not found", file.getName()));
            });
        file.setName(found.getFileName().toString());
    }

    @SneakyThrows private void replaceOldValueUnitPattern(FileInfo file) {
        final Path source = Paths.get(jbossHomePath.toAbsolutePath().toString(), file.getPath(), file.getName())
            .normalize();

        for (UnitValues<String, String> unit : file.getValues()) {
            if (unit.getOldValue().contains(PatternSymbol.SPECIAL_SYMBOL.getSymbol())) {
                Pattern pattern = Pattern.compile(
                    unit.getOldValue().replaceAll(PatternSymbol.SPECIAL_SYMBOL.getSymbol(), ".")
                );
                String sourceContent = Files.lines(source, StandardCharsets.UTF_8)
                    .map(String::trim)
                    .filter(row -> pattern.matcher(row).matches())
                    .findFirst()
                    .orElseThrow(() -> {
                        log.error("Content with pattern: [{}] not found", pattern.pattern());
                        return new NoSuchElementException(String.format("Content [%s] not found", unit.getOldValue()));
                    });
                unit.setOldValue(sourceContent);
            }
        }
    }
}
