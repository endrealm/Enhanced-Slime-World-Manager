package com.grinderwolf.eswm.nms.v1_13_R1;

import com.flowpowered.nbt.*;
import com.grinderwolf.eswm.api.utils.NibbleArray;
import com.grinderwolf.eswm.api.world.SlimeChunk;
import com.grinderwolf.eswm.api.world.SlimeChunkSection;
import com.grinderwolf.eswm.nms.CraftSlimeChunkSection;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.server.v1_13_R1.*;

import java.util.ArrayList;
import java.util.List;

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

        for (int sectionId = 0; sectionId < chunk.getSections().length; sectionId++) {
            ChunkSection section = chunk.getSections()[sectionId];

            if (section != null) {
                section.recalcBlockCounts();

                if (!section.a()) { // If the section is empty, just ignore it to save space
                    // Block Light Nibble Array
                    NibbleArray blockLightArray = Converter.convertArray(section.getEmittedLightArray());

                    // Sky light Nibble Array
                    NibbleArray skyLightArray = Converter.convertArray(section.getSkyLightArray());

                    // Block Data
                    DataPaletteBlock dataPaletteBlock = section.getBlocks();
                    NBTTagCompound blocksCompound = new NBTTagCompound();
                    dataPaletteBlock.b(blocksCompound, "Palette", "BlockStates");
                    NBTTagList paletteList = blocksCompound.getList("Palette", 10);
                    ListTag<CompoundTag> palette = (ListTag<CompoundTag>) Converter.convertTag("", paletteList);
                    long[] blockStates = blocksCompound.o("BlockStates");

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

        for (HeightMap.Type type : chunk.heightMap.keySet()) {
            heightMaps.put(type.b(), new LongArrayTag(type.b(), chunk.b(type).b()));
        }

       return new CompoundTag("", heightMaps);
    }

    @Override
    public int[] getBiomes() {
        BiomeBase[] biomeBases = chunk.getBiomeIndex();
        int[] biomes = new int[biomeBases.length];

        for (int i = 0; i < biomeBases.length; i++) {
            biomes[i] = BiomeBase.REGISTRY_ID.a(biomeBases[i]);
        }

        return biomes;
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
                    chunk.f(true);
                    entities.add((CompoundTag) Converter.convertTag("", entityNbt));
                }
            }
        }

        return entities;
    }
}
