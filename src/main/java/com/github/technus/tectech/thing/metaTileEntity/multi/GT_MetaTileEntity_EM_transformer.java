package com.github.technus.tectech.thing.metaTileEntity.multi;

import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.textureOffset;
import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static com.github.technus.tectech.thing.casing.TT_Container_Casings.sBlockCasingsTT;
import static com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM.HatchElement.DynamoMulti;
import static com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM.HatchElement.EnergyMulti;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.GregTech_API.sBlockCasings1;
import static gregtech.api.enums.GT_HatchElement.Dynamo;
import static gregtech.api.enums.GT_HatchElement.Energy;
import static gregtech.api.util.GT_StructureUtility.buildHatchAdder;
import static net.minecraft.util.StatCollector.translateToLocal;

import com.github.technus.tectech.Reference;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_Container_MultiMachineEM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_GUIContainer_MultiMachineEM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.github.technus.tectech.util.CommonValues;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IItemSource;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

/**
 * Created by danie_000 on 17.12.2016.
 */
public class GT_MetaTileEntity_EM_transformer extends GT_MetaTileEntity_MultiblockBase_EM
        implements ISurvivalConstructable {
    // region structure
    private static final String[] description = new String[] {
        EnumChatFormatting.AQUA + translateToLocal("tt.keyphrase.Hint_Details") + ":",
        translateToLocal(
                "gt.blockmachines.multimachine.em.transformer.hint"), // 1 - Energy IO Hatches or High Power Casing
    };
    private static final IStructureDefinition<GT_MetaTileEntity_EM_transformer> STRUCTURE_DEFINITION =
            IStructureDefinition.<GT_MetaTileEntity_EM_transformer>builder()
                    .addShape("main", new String[][] {
                        {
                            "111", "1~1", "111",
                        },
                        {
                            "111", "101", "111",
                        },
                        {
                            "111", "111", "111",
                        },
                    })
                    .addElement('0', ofBlock(sBlockCasings1, 15))
                    .addElement(
                            '1',
                            buildHatchAdder(GT_MetaTileEntity_EM_transformer.class)
                                    .atLeast(Energy, EnergyMulti, Dynamo, DynamoMulti)
                                    .casingIndex(textureOffset)
                                    .dot(1)
                                    .buildAndChain(onElementPass(t -> t.casingCount++, ofBlock(sBlockCasingsTT, 0))))
                    .build();
    private int casingCount = 0;

    @Override
    public IStructureDefinition<GT_MetaTileEntity_EM_transformer> getStructure_EM() {
        return STRUCTURE_DEFINITION;
    }
    // endregion

    public GT_MetaTileEntity_EM_transformer(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        mWrench = true;
        mScrewdriver = true;
        mSoftHammer = true;
        mHardHammer = true;
        mSolderingTool = true;
        mCrowbar = true;
        eDismantleBoom = true;
    }

    public GT_MetaTileEntity_EM_transformer(String aName) {
        super(aName);
        mWrench = true;
        mScrewdriver = true;
        mSoftHammer = true;
        mHardHammer = true;
        mSolderingTool = true;
        mCrowbar = true;
        eDismantleBoom = true;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_transformer(mName);
    }

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
        casingCount = 0;
        return structureCheck_EM("main", 1, 1, 0) && casingCount >= 5;
    }

    @Override
    public boolean checkRecipe_EM(ItemStack itemStack) {
        if (ePowerPass) {
            mEfficiencyIncrease = 10000;
            mMaxProgresstime = 20;
        } else {
            mEfficiencyIncrease = 0;
            mMaxProgresstime = 0;
        }
        eAmpereFlow = 0;
        mEUt = 0;
        return ePowerPass;
    }

    @Override
    public GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType(translateToLocal(
                        "gt.blockmachines.multimachine.em.transformer.name")) // Machine Type: Transformer
                .addInfo(translateToLocal(
                        "gt.blockmachines.multimachine.em.transformer.desc.0")) // Controller block of the
                // Active Transformer
                .addInfo(translateToLocal(
                        "gt.blockmachines.multimachine.em.transformer.desc.1")) // Can transform to and from any voltage
                .addInfo(translateToLocal(
                        "gt.blockmachines.multimachine.em.transformer.desc.2")) // Only 0.004% power loss, HAYO!
                .addInfo(translateToLocal(
                        "gt.blockmachines.multimachine.em.transformer.desc.3")) // Will explode if broken while running
                .addSeparator()
                .beginStructureBlock(3, 3, 3, false)
                .addController(translateToLocal("tt.keyword.Structure.FrontCenter")) // Controller: Front center
                .addCasingInfo(translateToLocal("gt.blockcasingsTT.0.name"), 5) // 5x High Power Casing (minimum)
                .addOtherStructurePart(
                        translateToLocal("tt.keyword.Structure.SuperconductingCoilBlock"),
                        translateToLocal("tt.keyword.Structure.Center")) // SuperconductingCoilBlock: Center
                .addEnergyHatch(
                        translateToLocal("tt.keyword.Structure.AnyHighPowerCasing"),
                        1) // Energy Hatch: Any High Power Casing
                .addDynamoHatch(
                        translateToLocal("tt.keyword.Structure.AnyHighPowerCasing"),
                        1) // Dynamo Hatch: Any High Power Casing
                .toolTipFinisher(CommonValues.TEC_MARK_GENERAL);
        return tt;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_MultiMachineEM(aPlayerInventory, aBaseMetaTileEntity, true, false, false);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachineEM(
                aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "EMDisplay.png", true, false, false);
    }

    @Override
    public ITexture[] getTexture(
            IGregTechTileEntity aBaseMetaTileEntity,
            byte aSide,
            byte aFacing,
            byte aColorIndex,
            boolean aActive,
            boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[] {
                Textures.BlockIcons.casingTexturePages[texturePage][0],
                new TT_RenderedExtendedFacingTexture(
                        aActive
                                ? GT_MetaTileEntity_MultiblockBase_EM.ScreenON
                                : GT_MetaTileEntity_MultiblockBase_EM.ScreenOFF)
            };
        }
        return new ITexture[] {Textures.BlockIcons.casingTexturePages[texturePage][0]};
    }

    public static final ResourceLocation activitySound = new ResourceLocation(Reference.MODID + ":fx_noise");

    @Override
    @SideOnly(Side.CLIENT)
    protected ResourceLocation getActivitySound() {
        return activitySound;
    }

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        return true;
    }

    @Override
    public boolean doRandomMaintenanceDamage() {
        return true;
    }

    @Override
    public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if ((aTick & 31) == 31) {
            ePowerPass = aBaseMetaTileEntity.isAllowedToWork();
        }
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        structureBuild_EM("main", 1, 1, 0, stackSize, hintsOnly);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, IItemSource source, EntityPlayerMP actor) {
        if (mMachine) return -1;
        return survivialBuildPiece("main", stackSize, 1, 1, 0, elementBudget, source, actor, false, true);
    }

    @Override
    public String[] getStructureDescription(ItemStack stackSize) {
        return description;
    }
}
