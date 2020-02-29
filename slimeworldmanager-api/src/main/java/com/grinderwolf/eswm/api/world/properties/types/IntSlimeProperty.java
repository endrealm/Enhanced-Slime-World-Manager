package com.grinderwolf.eswm.api.world.properties.types;

import com.flowpowered.nbt.IntTag;
import com.grinderwolf.eswm.api.world.properties.PropertyType;

import java.util.function.Function;

public class IntSlimeProperty extends SlimeProperty<Integer> {
    public IntSlimeProperty(String nbtName, PropertyType type, Integer defaultValue) {
        this(nbtName, type, defaultValue, null);
    }

    public IntSlimeProperty(String nbtName, PropertyType type, Integer defaultValue, Function<Integer, Boolean> validator) {
        super(nbtName, type, defaultValue, validator,
                (value, parent, self) -> parent.put(self.getNbtName(), new IntTag(self.getNbtName(), value)),
                (parent, self) -> parent.getIntValue(self.getNbtName()).orElse(self.getDefaultValue())
        );
    }
}
