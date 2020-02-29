package com.grinderwolf.eswm.api.world.properties.types;

import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.grinderwolf.eswm.api.world.properties.PropertyType;
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
    private final FromCompound<T> fromCompound;
    private final T defaultValue;
    private final Function<T, Boolean> validator;

    public SlimeProperty(String nbtName, PropertyType type, T defaultValue, ToCompound<T> toCompound, FromCompound<T> fromCompound) {
        this(nbtName, type, defaultValue, null, toCompound, fromCompound);
    }

    public SlimeProperty(String nbtName, PropertyType type, T defaultValue, Function<T, Boolean> validator, ToCompound<T> toCompound, FromCompound<T> fromCompound) {
        this.nbtName = nbtName;
        this.type = type;
        this.toCompound = toCompound;
        this.fromCompound = fromCompound;

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

    @FunctionalInterface
    public interface FromCompound<T> {
        T convert(CompoundTag parent, SlimeProperty<T> self);
    }
}
