package com.grinderwolf.eswm.nms.v1_15_R1;

import com.flowpowered.nbt.CompoundTag;
import com.grinderwolf.eswm.api.world.properties.SlimeProperties;
import com.grinderwolf.eswm.nms.CraftSlimeWorld;
import lombok.Getter;
import net.minecraft.server.v1_15_R1.*;

import java.util.Optional;

@Getter
public class CustomWorldData extends WorldData {

    private final CraftSlimeWorld world;
    private final WorldType type;

    CustomWorldData(CraftSlimeWorld world) {
        this.world = world;
        this.type = WorldType.getType(world.getPropertyMap().getString(SlimeProperties.WORLD_TYPE).toUpperCase());
        this.setGameType(EnumGamemode.NOT_SET);

        // Game rules
        CompoundTag extraData = world.getExtraData();
        Optional<CompoundTag> gameRules = extraData.getAsCompoundTag("gamerules");
        gameRules.ifPresent(compoundTag -> this.v().a((NBTTagCompound) Converter.convertTag(compoundTag)));
    }

    @Override
    public String getName() {
        return world.getName();
    }

    @Override
    public boolean u() {
        return true;
    }
}
