package com.main.service.filesystem.impl;

import com.main.domain.FileInfo;
import com.main.service.filesystem.Command;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

@Slf4j
@AllArgsConstructor
public class RegexpNameService implements Command<FileInfo> {
    private final Path jbossHomePath;

    @SneakyThrows @Override public void execute(FileInfo some) {
        final Path path = Paths.get(jbossHomePath.toAbsolutePath().toString(), some.getPath()).normalize();
        final Pattern pattern = Pattern.compile(some.getName());
        final String originalFileName = Files.list(path)
            .map(Path::getFileName)
            .map(Path::toString)
            .filter(pattern.asPredicate())
            .findFirst()
            .orElseThrow(() -> {
                log.error("File with pattern [{}] not found", some.getName());
                return new NoSuchFileException("File not found");
            });
        some.setName(originalFileName);
    }
}
