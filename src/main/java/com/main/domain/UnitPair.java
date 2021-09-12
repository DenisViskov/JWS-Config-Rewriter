package com.main.domain;

import lombok.Data;

@Data
public class UnitPair<K, V> {
    private final K key;
    private final V value;
}
