package com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules;

import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static net.minecraft.util.StatCollector.translateToLocal;

import java.util.ArrayList;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.github.technus.tectech.thing.casing.TT_Container_Casings;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.*;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureUtility;

import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IGlobalWirelessEnergy;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_StructureUtility;

public class GT_MetaTileEntity_EM_BaseModule extends GT_MetaTileEntity_MultiblockBase_EM
        implements IGlobalWirelessEnergy {

    protected final int tier = getTier();
    protected boolean isConnected = false;
    protected String userUUID = "";
    Parameters.Group.ParameterIn[] parallelParameter;
    static Parameters.Group.ParameterIn[] fuelConsumptionParameter;
    private int spacetimeCompressionFieldMetadata = -1;

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final IStructureDefinition<GT_MetaTileEntity_EM_BaseModule> STRUCTURE_DEFINITION = StructureDefinition
            .<GT_MetaTileEntity_EM_BaseModule>builder()
            .addShape(
                    STRUCTURE_PIECE_MAIN,
                    StructureUtility.transpose(
                            new String[][] { { "H", "H" }, { "~", "H" }, { "H", "H" }, { "H", "H" }, { "H", "H" } }))
            .addElement(
                    'H',
                    GT_StructureUtility.ofHatchAdderOptional(
                            GT_MetaTileEntity_EM_BaseModule::addClassicToMachineList,
                            texturePage << 7,
                            1,
                            TT_Container_Casings.sBlockCasingsBA0,
                            12))
            .build();

    public GT_MetaTileEntity_EM_BaseModule(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_EM_BaseModule(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_BaseModule(mName) {};
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide() && isConnected) {
            super.onPostTick(aBaseMetaTileEntity, aTick);
            if (aTick % 400 == 0) fixAllIssues();
            if (mEfficiency < 0) mEfficiency = 0;
            if (aBaseMetaTileEntity.getStoredEU() <= 0 && mMaxProgresstime > 0) {
                stopMachine();
            }
        }
    }

    @Nullable
    public static FluidStack tryConvertItemStackToFluidMaterial(ItemStack input) {
        ArrayList<String> oreDicts = new ArrayList<>();
        for (int id : OreDictionary.getOreIDs(input)) {
            oreDicts.add(OreDictionary.getOreName(id));
        }

        for (String dict : oreDicts) {
            OrePrefixes orePrefix;
            try {
                orePrefix = OrePrefixes.valueOf(findBestPrefix(dict));
            } catch (Exception e) {
                continue;
            }
        }
        return null;
    }

    private static String findBestPrefix(String oreDict) {
        int longestPrefixLength = 0;
        String matchingPrefix = null;
        for (OrePrefixes prefix : OrePrefixes.values()) {
            String name = prefix.toString();
            if (oreDict.startsWith(name)) {
                if (name.length() > longestPrefixLength) {
                    longestPrefixLength = name.length();
                    matchingPrefix = name;
                }
            }
        }
        return matchingPrefix;
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sBlastRecipes;
    }

    @Override
    public boolean drainEnergyInput(long EUtEffective, long Amperes) {
        long EU_drain = EUtEffective * Amperes;
        if (EU_drain == 0L) {
            return true;
        } else {
            if (EU_drain > 0L) {
                EU_drain = -EU_drain;
            }
            return addEUToGlobalEnergyMap(userUUID, EU_drain);
        }
    }

    @Override
    protected void parametersInstantiation_EM() {
        super.parametersInstantiation_EM();
        parallelParameter = new Parameters.Group.ParameterIn[1];
        fuelConsumptionParameter = new Parameters.Group.ParameterIn[1];
        parallelParameter[0] = parametrization.getGroup(0, false)
                .makeInParameter(0, 1, PARALLEL_PARAM_NAME, PARALLEL_AMOUNT);
    }

    private static final INameFunction<GT_MetaTileEntity_EM_BaseModule> PARALLEL_PARAM_NAME = (base,
            p) -> translateToLocal("gt.blockmachines.multimachine.FOG.parallel");
    // Parallel parameter value
    private static final IStatusFunction<GT_MetaTileEntity_EM_BaseModule> PARALLEL_AMOUNT = (base, p) -> LedStatus
            .fromLimitsInclusiveOuterBoundary(p.get(), 1, 0, base.getMaxParallels(), base.getMaxParallels());

    protected int getMaxParallels() {
        return (int) Math.pow(4, spacetimeCompressionFieldMetadata + 1);
    }

    public void connect() {
        isConnected = true;
    }

    public void disconnect() {
        isConnected = false;
    }

    protected void fixAllIssues() {
        mWrench = true;
        mScrewdriver = true;
        mSoftHammer = true;
        mHardHammer = true;
        mSolderingTool = true;
        mCrowbar = true;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public long getMaxInputVoltage() {
        return gregtech.api.enums.GT_Values.V[tier];
    }

    @Override
    public IStructureDefinition<? extends GT_MetaTileEntity_MultiblockBase_EM> getStructure_EM() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        structureBuild_EM(STRUCTURE_PIECE_MAIN, 0, 1, 0, stackSize, hintsOnly);
    }

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        fixAllIssues();
        return structureCheck_EM(STRUCTURE_PIECE_MAIN, 0, 1, 0);
    }

    @Override
    public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPreTick(aBaseMetaTileEntity, aTick);

        if (aTick == 1) {
            userUUID = String.valueOf(getBaseMetaTileEntity().getOwnerUuid());
            String userName = getBaseMetaTileEntity().getOwnerName();
            strongCheckOrAddUser(userUUID, userName);
        }
    }

    @Override
    public boolean isSimpleMachine() {
        return true;
    }

    @Override
    public boolean willExplodeInRain() {
        return false;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
            int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(texturePage << 7),
                    new TT_RenderedExtendedFacingTexture(
                            aActive ? GT_MetaTileEntity_MultiblockBase_EM.ScreenON
                                    : GT_MetaTileEntity_MultiblockBase_EM.ScreenOFF) };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(texturePage << 7) };
    }
}
