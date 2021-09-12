package com.main.service.conditions;

import java.util.Collection;

public interface EnvironmentCheck<V, T> {

    V checkEnvironmentVariables(Collection<T> variables);
}
