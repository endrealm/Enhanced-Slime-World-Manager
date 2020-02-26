package com.grinderwolf.swm.api.world.properties;

import com.grinderwolf.swm.api.world.properties.types.BooleanSlimeProperty;
import com.grinderwolf.swm.api.world.properties.types.IntSlimeProperty;
import com.grinderwolf.swm.api.world.properties.types.SlimeProperty;
import com.grinderwolf.swm.api.world.properties.types.StringSlimeProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class with all existing properties.
 */
public class SlimeProperties {

    public static final SlimeProperty<Integer> SPAWN_X = new IntSlimeProperty("spawnX", PropertyType.INT, 0);
    public static final SlimeProperty<Integer> SPAWN_Y = new IntSlimeProperty("spawnY", PropertyType.INT, 255);
    public static final SlimeProperty<Integer> SPAWN_Z = new IntSlimeProperty("spawnZ", PropertyType.INT, 0);

    public static final SlimeProperty<String> DIFFICULTY = new StringSlimeProperty("difficulty", PropertyType.STRING, "peaceful",
            (value) ->
                    value.equalsIgnoreCase("peaceful") || value.equalsIgnoreCase("easy")
                    || value.equalsIgnoreCase("normal") || value.equalsIgnoreCase("hard")
    );

    public static final SlimeProperty<Boolean> ALLOW_MONSTERS = new BooleanSlimeProperty("allowMonsters", PropertyType.BOOLEAN, true);
    public static final SlimeProperty<Boolean> ALLOW_ANIMALS = new BooleanSlimeProperty("allowAnimals", PropertyType.BOOLEAN, true);

    public static final SlimeProperty<Boolean> PVP = new BooleanSlimeProperty("pvp", PropertyType.BOOLEAN, true);

    public static final SlimeProperty<String> ENVIRONMENT = new StringSlimeProperty("environment", PropertyType.STRING, "normal",
            (value) ->
                    value.equalsIgnoreCase("normal") || value.equalsIgnoreCase("nether") || value.equalsIgnoreCase("the_end")
    );

    public static final SlimeProperty<String> WORLD_TYPE = new StringSlimeProperty("worldtype", PropertyType.STRING, "default",
            (value) ->
                    value.equalsIgnoreCase("default") || value.equalsIgnoreCase("flat") || value.equalsIgnoreCase("large_biomes")
                    || value.equalsIgnoreCase("amplified") || value.equalsIgnoreCase("customized")
                    || value.equalsIgnoreCase("debug_all_block_states") || value.equalsIgnoreCase("default_1_1")
    );

    public static final List<SlimeProperty<?>> VALUES = new ArrayList<>(Arrays.asList(SPAWN_X, SPAWN_Y, SPAWN_Z, DIFFICULTY, ALLOW_MONSTERS, ALLOW_ANIMALS, PVP, ENVIRONMENT, WORLD_TYPE));
}
