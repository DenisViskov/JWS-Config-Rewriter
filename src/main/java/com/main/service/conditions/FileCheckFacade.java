package com.main.service.conditions;

public interface FileCheckFacade<T, V> {

    T validateFile(V file);
}
