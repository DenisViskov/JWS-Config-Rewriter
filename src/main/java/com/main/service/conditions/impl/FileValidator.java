package com.main.service.conditions.impl;

import com.main.domain.FileInfo;
import com.main.domain.enums.EnvironmentVariable;
import com.main.domain.exceptions.NoSuchVariableException;
import com.main.service.conditions.EnvironmentCheck;
import com.main.service.conditions.FileCheck;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class FileValidator implements FileCheck<Boolean, FileInfo> {

    @Autowired
    private EnvironmentCheck<Boolean, EnvironmentVariable> environmentChecker;

    private String jbossHomePath;

    @SneakyThrows
    @PostConstruct
    private void initJbossHome() {
        if (!environmentChecker.checkEnvironmentVariables(Collections.singletonList(EnvironmentVariable.JBOSS_HOME))) {
            log.error("Environment variable {} does not exist", EnvironmentVariable.JBOSS_HOME.name());
            throw new NoSuchVariableException(String.format(
                "No such variable: %s",
                EnvironmentVariable.JBOSS_HOME.name()
            ));
        }
        jbossHomePath = System.getenv(EnvironmentVariable.JBOSS_HOME.name());
    }

    @Override public Boolean isExist(FileInfo file) {
        final Path path = Paths.get(jbossHomePath, file.getPath(), file.getName()).toAbsolutePath();
        return Files.exists(path.normalize());
    }

    @Override public Boolean hasAccess(FileInfo file) {
        final Path path = Paths.get(jbossHomePath, file.getPath(), file.getName()).toAbsolutePath();
        final Path normalizePath = path.normalize();
        return Files.isReadable(normalizePath) && Files.isWritable(normalizePath);
    }

    @Override public Boolean isDirectory(FileInfo file) {
        final Path path = Paths.get(jbossHomePath, file.getPath(), file.getName()).toAbsolutePath();
        return Files.isDirectory(path.normalize());
    }
}
