package com.github.technus.tectech.mechanics.pipe;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Tec on 26.02.2017.
 */
public interface IConnectsToElementalPipe {

    boolean canConnect(ForgeDirection side);
}
