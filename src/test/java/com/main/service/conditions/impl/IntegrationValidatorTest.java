package com.main.service.conditions.impl;

import com.main.domain.FileInfo;
import com.main.domain.enums.EnvironmentVariable;
import com.main.service.conditions.EnvironmentCheck;
import com.main.service.conditions.FileCheck;
import com.main.service.conditions.YamlCheck;
import com.main.store.Storage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
public class IntegrationValidatorTest {

    @Autowired
    private EnvironmentCheck<Boolean, EnvironmentVariable> environmentChecker;

    @Autowired
    private YamlCheck<Boolean, FileInfo> yamlChecker;

    @Autowired
    private FileCheck<Boolean, FileInfo> fileChecker;

    @Autowired
    private Storage<FileInfo> storage;

    @Test
    void testCommon() {
        final Boolean result = environmentChecker.checkEnvironmentVariables(
            Collections.singletonList(EnvironmentVariable.JBOSS_HOME)) &&
                               yamlChecker.validate(storage.getAll()) &&
                               storage.getAll().stream().allMatch(fileInfo -> fileChecker.isExist(fileInfo) &&
                                                                              fileChecker.hasAccess(fileInfo) &&
                                                                              !fileChecker.isDirectory(fileInfo));
    }
}
