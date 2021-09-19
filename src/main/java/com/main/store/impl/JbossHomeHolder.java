package com.main.store.impl;

import com.main.domain.enums.EnvironmentVariable;
import com.main.store.VariableStorage;
import lombok.AllArgsConstructor;

import java.nio.file.Path;

@AllArgsConstructor
public class JbossHomeHolder implements VariableStorage<Path> {

    private Path jbossHomePath;

    @Override public Path get(EnvironmentVariable variable) {
        return jbossHomePath;
    }
}
