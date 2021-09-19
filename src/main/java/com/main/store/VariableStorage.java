package com.main.store;

import com.main.domain.enums.EnvironmentVariable;

public interface VariableStorage<T> {

    T get(EnvironmentVariable variable);
}
