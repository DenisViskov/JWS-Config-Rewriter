package com.main.store;

import java.util.List;

public interface Storage<T> {

    List<T> getAll();
}
