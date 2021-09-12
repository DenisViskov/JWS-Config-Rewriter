package com.main.service.conditions;

import java.util.Collection;

public interface YamlCheck <V,T>{
    V validate(Collection<T> values);
}
