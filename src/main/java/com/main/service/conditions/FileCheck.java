package com.main.service.conditions;

public interface FileCheck<T, V> {
    T isExist(V file);

    T hasAccess(V file);

    T isDirectory(V file);
}
