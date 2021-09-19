package com.main.store.impl;

import com.main.domain.FileInfo;
import com.main.store.FileInfoStorage;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ParsedFileInfoHolder implements FileInfoStorage<FileInfo> {

    private List<FileInfo> fileInfoList;

    @Override public List<FileInfo> getAll() {
        return fileInfoList;
    }
}
