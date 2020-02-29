package com.grinderwolf.eswm.plugin.upgrade;

import com.grinderwolf.eswm.nms.CraftSlimeWorld;

public interface Upgrade {

    void upgrade(CraftSlimeWorld world);
    void downgrade(CraftSlimeWorld world);
}
