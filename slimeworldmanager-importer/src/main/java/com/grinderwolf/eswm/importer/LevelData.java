package com.grinderwolf.eswm.importer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
class LevelData {

    private final int version;
    private final Map<String, String> gameRules;
}
