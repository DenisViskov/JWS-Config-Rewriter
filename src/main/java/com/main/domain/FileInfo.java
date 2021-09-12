package com.main.domain;

import lombok.Data;

@Data
public class FileInfo {
    private final String name;
    private final String path;
    private final UnitPair<String, String> unit;
}
