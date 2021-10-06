package com.main.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnitValues<K, V> {
    private K oldValue;
    private V newValue;
    private boolean oldValueRegexp;
}
