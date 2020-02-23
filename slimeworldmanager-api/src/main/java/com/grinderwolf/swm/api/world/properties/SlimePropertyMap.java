package com.grinderwolf.swm.api.world.properties;

import com.flowpowered.nbt.*;
import com.grinderwolf.swm.api.world.properties.types.SlimeProperty;
import lombok.*;

import java.util.*;

/**
 * A Property Map object.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SlimePropertyMap {

    @Getter(value = AccessLevel.PRIVATE)
    private final Map<SlimeProperty<?>, Object> values;

    public SlimePropertyMap() {
        this(new HashMap<>());
    }

    /**
     * Returns the string value of a given property.
     *
     * @param property The property to retrieve the value of.
     * @return the {@link String} value of the property or the default value if unset.
     * @throws IllegalArgumentException if the property type is not {@link PropertyType#STRING}.
     */
    @Deprecated
    @SuppressWarnings({"unchecked", "rawtypes"})
    public String getString(SlimeProperty property) {
        ensureType(property, PropertyType.STRING);

        return get((SlimeProperty<String>) property);
    }

    /**
     * Updates a property with a given string value.
     *
     * @param property The property to update.
     * @param value The value to set.
     * @throws IllegalArgumentException if the property type is not {@link PropertyType#STRING}.
     */
    @Deprecated
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setString(SlimeProperty property, String value) {
        Objects.requireNonNull(value, "Property value cannot be null");
        ensureType(property, PropertyType.STRING);

        set(property, value);
    }

    /**
     * Returns the boolean value of a given property.
     *
     * @param property The property to retrieve the value of.
     * @return the {@link Boolean} value of the property or the default value if unset.
     * @throws IllegalArgumentException if the property type is not {@link PropertyType#BOOLEAN}.
     */
    @Deprecated
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Boolean getBoolean(SlimeProperty property) {
        ensureType(property, PropertyType.BOOLEAN);

        return get((SlimeProperty<Boolean>) property);
    }

    /**
     * Updates a property with a given boolean value.
     *
     * @param property The property to update.
     * @param value The value to set.
     * @throws IllegalArgumentException if the property type is not {@link PropertyType#BOOLEAN}.
     */
    @Deprecated
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setBoolean(SlimeProperty property, boolean value) {
        ensureType(property, PropertyType.BOOLEAN);
        set((SlimeProperty<Boolean>) property, value);
    }

    /**
     * Returns the int value of a given property.
     *
     * @param property The property to retrieve the value of.
     * @return the int value of the property or the default value if unset.
     * @throws IllegalArgumentException if the property type is not {@link PropertyType#INT}.
     */
    @Deprecated
    @SuppressWarnings({"unchecked", "rawtypes"})
    public int getInt(SlimeProperty property) {
        ensureType(property, PropertyType.INT);
        return get((SlimeProperty<Integer>) property);
    }

    /**
     * Returns the value of a given property.
     *
     * @param property The property to retrieve the value of.
     * @param <T> the type of the properties value
     * @return the int value of the property or the default value if unset.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(SlimeProperty<T> property) {
        T value = (T) values.get(property);

        if (value == null) {
            value = property.getDefaultValue();
        }

        return value;
    }

    /**
     * Updates a property with a given value.
     *
     * @param property The property to update.
     * @param value The value to set.
     * @param <T> the type of the properties value
     */
    public <T> void set(SlimeProperty<T> property, T value) {

        Objects.requireNonNull(value, "Property value cannot be null");

        if (property.getValidator() != null && !property.getValidator().apply(value)) {
            throw new IllegalArgumentException("'" + value + "' is not a valid property value.");
        }

        values.put(property, value);
    }

    /**
     * Updates a property with a given int value.
     *
     * @param property The property to update.
     * @param value The value to set.
     * @throws IllegalArgumentException if the property type is not {@link PropertyType#INT}.
     */
    @Deprecated
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setInt(SlimeProperty property, int value) {
        ensureType(property, PropertyType.INT);

        set(property, value);
    }

    private void ensureType(SlimeProperty property, PropertyType requiredType) {
        if (property.getType() != requiredType) {
            throw new IllegalArgumentException("Property " + property.getNbtName() + " type is " + property.getType().name() + ", not " + requiredType.name());
        }
    }

    /**
     * Copies all values from the specified {@link SlimePropertyMap}.
     * If the same property has different values on both maps, the one
     * on the providen map will be used.
     *
     * @param propertyMap A {@link SlimePropertyMap}.
     */
    public void merge(SlimePropertyMap propertyMap) {
        values.putAll(propertyMap.getValues());
    }

    /**
     * Returns a {@link CompoundTag} containing every property set in this map.
     *
     * @return A {@link CompoundTag} with all the properties stored in this map.
     */
    @SuppressWarnings({"rawtypes", "RawTypeCanBeGeneric", "unchecked"})
    public CompoundTag toCompound() {
        CompoundMap map = new CompoundMap();

        for (Map.Entry<SlimeProperty<?>, Object> entry : values.entrySet()) {
            SlimeProperty property = entry.getKey();
            Object value = entry.getValue();

            property.getToCompound().convert(value, map, property);
        }

        return new CompoundTag("properties", map);
    }

    /**
     * Creates a {@link SlimePropertyMap} based off a {@link CompoundTag}.
     *
     * @param compound A {@link CompoundTag} to get the properties from.
     * @return A {@link SlimePropertyMap} with the properties from the provided {@link CompoundTag}.
     */
    public static SlimePropertyMap fromCompound(CompoundTag compound) {
        Map<SlimeProperty<?>, Object> values = new HashMap<>();

        for (SlimeProperty<?> property : SlimeProperties.VALUES) {
            switch (property.getType()) {
                case STRING:
                    compound.getStringValue(property.getNbtName()).ifPresent((value) -> values.put(property, value));
                    break;
                case BOOLEAN:
                    compound.getByteValue(property.getNbtName()).map((value) -> value == 1).ifPresent((value) -> values.put(property, value));
                    break;
                case INT:
                    compound.getIntValue(property.getNbtName()).ifPresent((value) -> values.put(property, value));
                    break;
            }
        }

        return new SlimePropertyMap(values);
    }
}
