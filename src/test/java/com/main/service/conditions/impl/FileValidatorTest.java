package com.main.service.conditions.impl;

import com.main.domain.FileInfo;
import com.main.service.conditions.FileCheck;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class FileValidatorTest {

    private static FileCheck<Boolean, FileInfo> FILE_CHECKER;

    @BeforeAll
    static void beforeAll() {
        final String replacedPath = Paths.get("").toAbsolutePath().toString();
        FILE_CHECKER = new FileValidator(replacedPath);
    }

    @Test
    void isExist() {
        final FileInfo fileInfoExists = new FileInfo();
        fileInfoExists.setName("configuration.yaml");
        fileInfoExists.setPath("\\template");

        final FileInfo fileNotExists = new FileInfo();
        fileNotExists.setName("configuration.yaml");
        fileNotExists.setPath("\\templatesssss");

        assertThat(FILE_CHECKER.isExist(fileInfoExists)).isTrue();
        assertThat(FILE_CHECKER.isExist(fileNotExists)).isFalse();
    }

    @Test
    void hasAccess() {
        final FileInfo fileInfo = new FileInfo();
        fileInfo.setName("configuration.yaml");
        fileInfo.setPath("\\template");

        assertThat(FILE_CHECKER.hasAccess(fileInfo)).isTrue();
    }

    @Test
    void isDirectory() {
        final FileInfo fileInfo = new FileInfo();
        fileInfo.setName("");
        fileInfo.setPath("\\template");

        assertThat(FILE_CHECKER.isDirectory(fileInfo)).isTrue();

        fileInfo.setName("configuration.yaml");
        assertThat(FILE_CHECKER.isDirectory(fileInfo)).isFalse();
    }
}