package com.main.service.conditions.impl;

import com.main.domain.FileInfo;
import com.main.domain.UnitValues;
import com.main.service.conditions.YamlCheck;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class YamlValidatorTest {

    @Autowired
    YamlCheck<Boolean, FileInfo> checker;

    @Test
    void validate() {
        UnitValues<String,String> unit = new UnitValues<>();
        FileInfo info = new FileInfo();
        info.setName("name");
        info.setValues(Collections.singletonList(unit));
        Boolean result = checker.validate(Collections.singletonList(info));
        assertThat(result).isFalse();
    }
}