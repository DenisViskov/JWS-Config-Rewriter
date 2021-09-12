package com.main.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FileInfo {
    private String name;
    private String path;
    private List<UnitValues<String, String>> values;
}
