package com.main.service.filesystem.impl;

import com.main.domain.FileInfo;
import com.main.service.filesystem.Command;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Command<FileInfo> {
    private final List<Command<FileInfo>> components = new ArrayList<>();

    public void addComponent(final Command<FileInfo> command) {
        components.add(command);
    }

    @Override public void execute(FileInfo some) {
        components.forEach(command -> command.execute(some));
    }
}
