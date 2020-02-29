package com.grinderwolf.eswm.api.world.properties.types;

import com.flowpowered.nbt.StringTag;
import com.grinderwolf.eswm.api.world.properties.PropertyType;

import java.util.function.Function;

public class StringSlimeProperty extends SlimeProperty<String> {
    public StringSlimeProperty(String nbtName, PropertyType type, String defaultValue) {
        this(nbtName, type, defaultValue, null);
    }

    public StringSlimeProperty(String nbtName, PropertyType type, String defaultValue, Function<String, Boolean> validator) {
        super(nbtName, type, defaultValue, validator,
                (value, parent, self) -> parent.put(self.getNbtName(), new StringTag(self.getNbtName(), value)),
                (parent, self) -> parent.getStringValue(self.getNbtName()).orElse(self.getDefaultValue())
        );
    }
}
