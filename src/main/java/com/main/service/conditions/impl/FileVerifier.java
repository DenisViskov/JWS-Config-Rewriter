package com.main.service.conditions.impl;

import com.main.domain.FileInfo;
import com.main.domain.enums.EnvironmentVariable;
import com.main.domain.exceptions.NoSuchVariableException;
import com.main.service.conditions.FileCheck;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class FileVerifier implements FileCheck<Boolean, FileInfo> {

    private String jbossHomePath;

    @SneakyThrows
    @PostConstruct
    private void initJbossHome() {
        jbossHomePath = Optional.ofNullable(System.getenv(EnvironmentVariable.JBOSS_HOME.name()))
            .orElseThrow(() -> {
                log.error("Environment variable {} does not exist", EnvironmentVariable.JBOSS_HOME.name());
                return new NoSuchVariableException(String.format(
                    "No such variable: %s",
                    EnvironmentVariable.JBOSS_HOME.name()
                ));
            });
    }

    @Override public Boolean isExist(FileInfo file) {
        log.info("Checking existing file: {}", file.getName());
        final Path path = Paths.get(jbossHomePath, file.getPath(), file.getName()).toAbsolutePath();
        return Files.exists(path.normalize());
    }

    @Override public Boolean hasAccess(FileInfo file) {
        log.info("Checking access at {}", file.getName());
        final Path path = Paths.get(jbossHomePath, file.getPath(), file.getName()).toAbsolutePath();
        final Path normalizePath = path.normalize();
        return Files.isReadable(normalizePath) && Files.isWritable(normalizePath);
    }

    @Override public Boolean isDirectory(FileInfo file) {
        log.info("Checking that is not a directory: {}", file.getName());
        final Path path = Paths.get(jbossHomePath, file.getPath(), file.getName()).toAbsolutePath();
        return Files.isDirectory(path.normalize());
    }
}
