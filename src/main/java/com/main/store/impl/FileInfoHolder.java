package com.main.store.impl;

import com.main.domain.FileInfo;
import com.main.store.Storage;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FileInfoHolder implements Storage<FileInfo> {

    private List<FileInfo> fileInfoList;

    @Override
    public List<FileInfo> getAll() {
        return fileInfoList;
    }
}
