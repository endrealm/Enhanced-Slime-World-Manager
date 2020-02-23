package com.grinderwolf.swm.api.world.properties.types;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.grinderwolf.swm.api.world.properties.PropertyType;
import lombok.Getter;

import java.util.function.Function;

/**
 * A property object.
 */
@Getter
public class SlimeProperty<T> {

    private final String nbtName;
    private final PropertyType type;
    private final ToCompound<T> toCompound;
    private final T defaultValue;
    private final Function<T, Boolean> validator;

    public SlimeProperty(String nbtName, PropertyType type, T defaultValue, ToCompound<T> toCompound) {
        this(nbtName, type, defaultValue, null, toCompound);
    }

    public SlimeProperty(String nbtName, PropertyType type, T defaultValue, Function<T, Boolean> validator, ToCompound<T> toCompound) {
        this.nbtName = nbtName;
        this.type = type;
        this.toCompound = toCompound;

        if (defaultValue != null) {
            if (!type.getValueClazz().isInstance(defaultValue)) {
                throw new IllegalArgumentException(defaultValue + " does not match class " + type.getValueClazz().getName());
            }

            if (validator != null) {
                if (!validator.apply(defaultValue)) {
                    throw new IllegalArgumentException("Invalid default value for property " + nbtName + "! " + defaultValue);
                }
            }
        }

        this.defaultValue = defaultValue;
        this.validator = validator;
    }

    @FunctionalInterface
    public interface ToCompound<T> {
        void convert(T value, CompoundMap parent, SlimeProperty<T> self);
    }
}
