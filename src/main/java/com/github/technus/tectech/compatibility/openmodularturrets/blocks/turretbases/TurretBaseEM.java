package com.github.technus.tectech.compatibility.openmodularturrets.blocks.turretbases;

import static com.github.technus.tectech.TecTech.creativeTabTecTech;

import com.github.technus.tectech.Reference;
import com.github.technus.tectech.compatibility.openmodularturrets.tileentity.turretbase.TileTurretBaseEM;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import openmodularturrets.blocks.turretbases.BlockAbstractTurretBase;
import openmodularturrets.handler.ConfigHandler;

/**
 * Created by Tec on 27/07/2017.
 */
public class TurretBaseEM extends BlockAbstractTurretBase {
    private final int MaxCharge = ConfigHandler.getBaseTierFiveMaxCharge();
    private final int MaxIO = ConfigHandler.getBaseTierFiveMaxIo();
    public static TurretBaseEM INSTANCE;

    public TurretBaseEM() {
        setCreativeTab(creativeTabTecTech);
        setResistance(16);
        setBlockName("turretBaseEM");
        setStepSound(Block.soundTypeMetal);
        setBlockTextureName(Reference.MODID + ":turretBaseEM");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTurretBaseEM(MaxCharge, MaxIO);
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        super.registerBlockIcons(p_149651_1_);
        blockIcon = p_149651_1_.registerIcon(Reference.MODID + ":turretBaseEM");
    }

    public static void run() {
        INSTANCE = new TurretBaseEM();
        GameRegistry.registerBlock(INSTANCE, TurretBaseItemEM.class, INSTANCE.getUnlocalizedName());
        GameRegistry.registerTileEntity(TileTurretBaseEM.class, "TileTurretBaseEM");
    }
}
