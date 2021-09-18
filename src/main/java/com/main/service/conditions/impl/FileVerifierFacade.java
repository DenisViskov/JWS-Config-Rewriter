package com.main.service.conditions.impl;

import com.main.domain.FileInfo;
import com.main.domain.enums.PatternSymbol;
import com.main.service.conditions.FileCheck;
import com.main.service.conditions.FileCheckFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Slf4j
@Service
public class FileVerifierFacade implements FileCheckFacade<Boolean, FileInfo> {

    @Autowired
    private FileCheck<Boolean, FileInfo> fileChecker;

    @Override public Boolean validateFile(FileInfo file) {
        return null;
    }

    private void replacePatternIfPresent(FileInfo file) {
        final String name = file.getName();
        final String specialSymbol = PatternSymbol.X.name().toLowerCase();
        if (!name.contains(specialSymbol)) {
            return;
        }
        final Pattern pattern = Pattern.compile(name.replaceAll(specialSymbol,".{1}"));

    }
}
