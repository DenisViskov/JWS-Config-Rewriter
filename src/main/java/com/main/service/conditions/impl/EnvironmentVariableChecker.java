package com.main.service.conditions.impl;

import com.main.domain.enums.EnvironmentVariable;
import com.main.service.conditions.EnvironmentCheck;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class EnvironmentVariableChecker implements EnvironmentCheck<Boolean, EnvironmentVariable>{

    @Override
    public Boolean checkEnvironmentVariables(Collection<EnvironmentVariable> variables) {
        return variables.stream()
                .allMatch(var -> Objects.nonNull(System.getenv(var.name())));
    }
}
