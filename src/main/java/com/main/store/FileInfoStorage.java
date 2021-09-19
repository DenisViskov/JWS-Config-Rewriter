package com.main.store;

import java.util.List;

public interface FileInfoStorage<T> {

    List<T> getAll();
}
