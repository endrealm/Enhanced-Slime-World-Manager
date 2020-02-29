package com.grinderwolf.eswm.nms.v1_15_R1;

import com.flowpowered.nbt.*;
import com.grinderwolf.eswm.api.utils.NibbleArray;
import com.grinderwolf.eswm.api.world.SlimeChunk;
import com.grinderwolf.eswm.api.world.SlimeChunkSection;
import com.grinderwolf.eswm.nms.CraftSlimeChunkSection;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.server.v1_15_R1.*;

import java.util.*;

@Data
@AllArgsConstructor
public class NMSSlimeChunk implements SlimeChunk {

    private Chunk chunk;

    @Override
    public String getWorldName() {
        return chunk.getWorld().getWorldData().getName();
    }

    @Override
    public int getX() {
        return chunk.getPos().x;
    }

    @Override
    public int getZ() {
        return chunk.getPos().z;
    }

    @Override
    public SlimeChunkSection[] getSections() {
        SlimeChunkSection[] sections = new SlimeChunkSection[16];
        LightEngine lightEngine = chunk.world.getChunkProvider().getLightEngine();

        for (int sectionId = 0; sectionId < chunk.getSections().length; sectionId++) {
            ChunkSection section = chunk.getSections()[sectionId];

            if (section != null) {
                section.recalcBlockCounts();

                if (!section.c()) { // If the section is empty, just ignore it to save space
                    // Block Light Nibble Array
                    NibbleArray blockLightArray = Converter.convertArray(lightEngine.a(EnumSkyBlock.BLOCK).a(SectionPosition.a(chunk.getPos(), sectionId)));

                    // Sky light Nibble Array
                    NibbleArray skyLightArray = Converter.convertArray(lightEngine.a(EnumSkyBlock.SKY).a(SectionPosition.a(chunk.getPos(), sectionId)));

                    // Block Data
                    DataPaletteBlock dataPaletteBlock = section.getBlocks();
                    NBTTagCompound blocksCompound = new NBTTagCompound();
                    dataPaletteBlock.a(blocksCompound, "Palette", "BlockStates");
                    NBTTagList paletteList = blocksCompound.getList("Palette", 10);
                    ListTag<CompoundTag> palette = (ListTag<CompoundTag>) Converter.convertTag("", paletteList);
                    long[] blockStates = blocksCompound.getLongArray("BlockStates");

                    sections[sectionId] = new CraftSlimeChunkSection(null, null, palette, blockStates, blockLightArray, skyLightArray);
                }
            }
        }

        return sections;
    }

    @Override
    public CompoundTag getHeightMaps() {
        // HeightMap
        CompoundMap heightMaps = new CompoundMap();

        for (Map.Entry<HeightMap.Type, HeightMap> entry : chunk.heightMap.entrySet()) {
            HeightMap.Type type = entry.getKey();
            HeightMap map = entry.getValue();

            heightMaps.put(type.a(), new LongArrayTag(type.a(), map.a()));
        }

       return new CompoundTag("", heightMaps);
    }

    @Override
    public int[] getBiomes() {
        return chunk.getBiomeIndex().a();
    }

    @Override
    public List<CompoundTag> getTileEntities() {
        List<CompoundTag> tileEntities = new ArrayList<>();

        for (TileEntity entity : chunk.getTileEntities().values()) {
            NBTTagCompound entityNbt = new NBTTagCompound();
            entity.save(entityNbt);
            tileEntities.add((CompoundTag) Converter.convertTag("", entityNbt));
        }

        return tileEntities;
    }

    @Override
    public List<CompoundTag> getEntities() {
        List<CompoundTag> entities = new ArrayList<>();

        for (int i = 0; i < chunk.getEntitySlices().length; i++) {
            for (Entity entity : chunk.getEntitySlices()[i]) {
                NBTTagCompound entityNbt = new NBTTagCompound();

                if (entity.d(entityNbt)) {
                    chunk.d(true);
                    entities.add((CompoundTag) Converter.convertTag("", entityNbt));
                }
            }
        }

        return entities;
    }
}
