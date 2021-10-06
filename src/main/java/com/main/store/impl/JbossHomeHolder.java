package com.main.store.impl;

import com.main.store.JbossHomeStorage;
import lombok.AllArgsConstructor;

import java.nio.file.Path;

@AllArgsConstructor
public class JbossHomeHolder implements JbossHomeStorage<Path> {

    private Path jbossHomePath;

    @Override public Path get() {
        return jbossHomePath;
    }
}
