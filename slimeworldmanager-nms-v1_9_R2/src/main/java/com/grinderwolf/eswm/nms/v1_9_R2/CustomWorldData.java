package com.grinderwolf.eswm.nms.v1_9_R2;

import com.flowpowered.nbt.CompoundTag;
import com.grinderwolf.eswm.api.world.properties.SlimeProperties;
import com.grinderwolf.eswm.nms.CraftSlimeWorld;
import lombok.Getter;
import net.minecraft.server.v1_9_R2.*;

import java.util.Optional;

@Getter
public class CustomWorldData extends WorldData {

    private final CraftSlimeWorld world;
    private final WorldType type;

    CustomWorldData(CraftSlimeWorld world) {
        this.world = world;
        this.type = WorldType.getType(world.getPropertyMap().getString(SlimeProperties.WORLD_TYPE).toUpperCase());
        this.setGameType(WorldSettings.EnumGamemode.NOT_SET);

        // Game rules
        CompoundTag extraData = world.getExtraData();
        Optional<CompoundTag> gameRules = extraData.getAsCompoundTag("gamerules");
        gameRules.ifPresent(compoundTag -> this.w().a((NBTTagCompound) Converter.convertTag(compoundTag)));
    }

    @Override
    public String getName() {
        return world.getName();
    }

}
