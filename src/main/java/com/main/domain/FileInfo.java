package com.main.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FileInfo {
    private String name;
    private String path;
    private boolean nameRegexp;
    private List<UnitValues<String, String>> values;
}
