package com.main.service.conditions.impl;

import com.main.domain.FileInfo;
import com.main.service.conditions.YamlCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Service
public class YamlValidator implements YamlCheck<Boolean, FileInfo> {

    @Override
    public Boolean validate(Collection<FileInfo> values) {
        log.info("validating configuration.yaml values: {}", values);
        return values.stream()
            .flatMap(file -> Stream.concat(
                    Stream.of(file.getName(), file.getPath()),
                    file.getValues().stream().flatMap(unit -> Stream.of(
                        unit.getOldValue(),
                        unit.getNewValue()
                    ))
                ))
            .allMatch(Objects::nonNull);
    }
}
