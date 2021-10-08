package com.main.service.filesystem;

import lombok.SneakyThrows;

public interface Command<T> {

    void execute(T some);
}
