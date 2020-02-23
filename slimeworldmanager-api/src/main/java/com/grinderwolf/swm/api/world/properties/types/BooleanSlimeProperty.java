package com.grinderwolf.swm.api.world.properties.types;

import com.flowpowered.nbt.ByteTag;
import com.grinderwolf.swm.api.world.properties.PropertyType;

import java.util.function.Function;

public class BooleanSlimeProperty extends SlimeProperty<Boolean> {
    public BooleanSlimeProperty(String nbtName, PropertyType type, Boolean defaultValue) {
        this(nbtName, type, defaultValue, null);
    }

    public BooleanSlimeProperty(String nbtName, PropertyType type, Boolean defaultValue, Function<Boolean, Boolean> validator) {
        super(nbtName, type, defaultValue, validator,
                (value, parent, self) -> parent.put(self.getNbtName(), new ByteTag(self.getNbtName(), (byte) (value ? 1 : 0)))
        );
    }
}
