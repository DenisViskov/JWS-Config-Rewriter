package com.main.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PatternSymbol {
    SPECIAL_SYMBOL("~");

    private final String symbol;
}
