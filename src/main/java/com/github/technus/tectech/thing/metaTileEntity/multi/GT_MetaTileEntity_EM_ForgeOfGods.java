package com.github.technus.tectech.thing.metaTileEntity.multi;

import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static com.github.technus.tectech.thing.casing.TT_Container_Casings.sBlockCasingsBA0;
import static com.github.technus.tectech.thing.casing.TT_Container_Casings.sBlockCasingsTT;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.enums.GT_HatchElement.*;
import static gregtech.api.util.GT_StructureUtility.buildHatchAdder;
import static gregtech.api.util.GT_Utility.formatNumbers;
import static net.minecraft.util.StatCollector.translateToLocal;

import java.util.*;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import org.apache.commons.lang3.tuple.Pair;

import com.github.technus.tectech.thing.casing.TT_Container_Casings;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.*;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.github.technus.tectech.thing.metaTileEntity.multi.godforge_modules.GT_MetaTileEntity_EM_BaseModule;
import com.github.technus.tectech.util.CommonValues;
import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.alignment.constructable.IConstructable;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IItemSource;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.interfaces.IGlobalWirelessEnergy;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.util.*;
import gregtech.common.tileentities.machines.GT_MetaTileEntity_Hatch_OutputBus_ME;
import gregtech.common.tileentities.machines.GT_MetaTileEntity_Hatch_Output_ME;

public class GT_MetaTileEntity_EM_ForgeOfGods extends GT_MetaTileEntity_MultiblockBase_EM
        implements IConstructable, IGlobalWirelessEnergy, ISurvivalConstructable {

    // region variables
    private static Textures.BlockIcons.CustomIcon ScreenOFF;
    private static Textures.BlockIcons.CustomIcon ScreenON;

    public static Parameters.Group.ParameterIn fuelConsumptionParameter;
    public ArrayList<GT_MetaTileEntity_EM_BaseModule> moduleHatches = new ArrayList<>();

    private static int spacetimeCompressionFieldMetadata = -1;
    private int solenoidCoilMetadata = -1;
    private static final int MODULE_CHECK_INTERVAL = 100;

    private GT_MetaTileEntity_Hatch_Input fuelInputHatch;
    private String userUUID = "";
    protected static final String STRUCTURE_PIECE_MAIN = "main";

    public int survivalConstruct(ItemStack stackSize, int elementBudget, IItemSource source, EntityPlayerMP actor) {
        if (mMachine) return -1;
        int realBudget = elementBudget >= 200 ? elementBudget : Math.min(200, elementBudget * 5); // 200 blocks max per
        // placement.
        return survivialBuildPiece(STRUCTURE_PIECE_MAIN, stackSize, 31, 34, 0, realBudget, source, actor, false, true);
    }

    @Override
    public IStructureDefinition<GT_MetaTileEntity_EM_ForgeOfGods> getStructure_EM() {
        return STRUCTURE_DEFINITION;
    }

    public static final IStructureDefinition<GT_MetaTileEntity_EM_ForgeOfGods> STRUCTURE_DEFINITION = IStructureDefinition
            .<GT_MetaTileEntity_EM_ForgeOfGods>builder()
            .addShape("main", transpose(ForgeofGodsStructureString.godForge))

            .addElement(
                    'A',
                    buildHatchAdder(GT_MetaTileEntity_EM_ForgeOfGods.class)
                            .atLeast(InputHatch, OutputHatch, InputBus, OutputBus).casingIndex(texturePage << 7).dot(1)
                            .buildAndChain(sBlockCasingsBA0, 12))
            .addElement('B', ofBlock(sBlockCasingsTT, 11)).addElement('C', ofBlock(sBlockCasingsTT, 12))
            .addElement(
                    'D',
                    ofBlocksTiered(
                            (block, meta) -> block == GregTech_API.sSolenoidCoilCasings ? meta : -1,
                            ImmutableList.of(
                                    Pair.of(GregTech_API.sSolenoidCoilCasings, 7),
                                    Pair.of(GregTech_API.sSolenoidCoilCasings, 8),
                                    Pair.of(GregTech_API.sSolenoidCoilCasings, 9),
                                    Pair.of(GregTech_API.sSolenoidCoilCasings, 10)),
                            -1,
                            (t, meta) -> t.solenoidCoilMetadata = meta,
                            t -> t.solenoidCoilMetadata))
            .addElement(
                    'E',
                    ofBlocksTiered(
                            (block, meta) -> block == TT_Container_Casings.SpacetimeCompressionFieldGenerators ? meta
                                    : -1,
                            ImmutableList.of(
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 0),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 1),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 2),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 3),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 4),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 5),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 6),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 7),
                                    Pair.of(TT_Container_Casings.SpacetimeCompressionFieldGenerators, 8)),
                            -1,
                            (t, meta) -> spacetimeCompressionFieldMetadata = meta,
                            t -> spacetimeCompressionFieldMetadata))
            .addElement(
                    'F',
                    buildHatchAdder(GT_MetaTileEntity_EM_ForgeOfGods.class)
                            .hatchClass(GT_MetaTileEntity_Hatch_Input.class)
                            .adder(GT_MetaTileEntity_EM_ForgeOfGods::addFuelInputToMachineList)
                            .casingIndex(texturePage << 7).dot(2).buildAndChain(sBlockCasingsBA0, 12))
            .addElement(
                    'G',
                    GT_HatchElementBuilder.<GT_MetaTileEntity_EM_ForgeOfGods>builder().atLeast(moduleElement.Module)
                            .casingIndex(texturePage << 7).dot(2).buildAndChain(sBlockCasingsBA0, 12))
            .build();

    public GT_MetaTileEntity_EM_ForgeOfGods(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_EM_ForgeOfGods(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_ForgeOfGods(mName);
    }

    @Override
    public GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType(translateToLocal("gt.blockmachines.multimachine.em.blackholegenerator.name")) // Machine Type:
                                                                                                        // Black Hole
                                                                                                        // Generator
                .addInfo(translateToLocal("gt.blockmachines.multimachine.em.blackholegenerator.desc.0")) // Controller
                                                                                                         // block of the
                                                                                                         // Black Hole
                                                                                                         // Generator
                .addInfo(translateToLocal("gt.blockmachines.multimachine.em.blackholegenerator.desc.1")) // Uses a black
                                                                                                         // hole to
                                                                                                         // generate
                                                                                                         // power
                .addInfo(translateToLocal("tt.keyword.Structure.StructureTooComplex")) // The structure is too complex!
                .addSeparator().beginStructureBlock(33, 33, 33, false)
                .addEnergyHatch(translateToLocal("tt.keyword.Structure.AnyHighPowerCasing1D"), 1) // Energy Hatch: Any
                                                                                                  // High Power Casing
                                                                                                  // with 1 dot
                .addMaintenanceHatch(translateToLocal("tt.keyword.Structure.AnyHighPowerCasing1D"), 1) // Maintenance
                                                                                                       // Hatch: Any
                                                                                                       // High Power
                                                                                                       // Casing with 1
                                                                                                       // dot
                .toolTipFinisher(CommonValues.TEC_MARK_EM);
        return tt;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aBlockIconRegister) {
        ScreenOFF = new Textures.BlockIcons.CustomIcon("iconsets/EM_BHG");
        ScreenON = new Textures.BlockIcons.CustomIcon("iconsets/EM_BHG_ACTIVE");
        super.registerIcons(aBlockIconRegister);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection facing,
            int colorIndex, boolean aActive, boolean aRedstone) {
        if (side == facing) {
            return new ITexture[] { Textures.BlockIcons.casingTexturePages[texturePage][12],
                    new TT_RenderedExtendedFacingTexture(aActive ? ScreenON : ScreenOFF) };
        }
        return new ITexture[] { Textures.BlockIcons.casingTexturePages[texturePage][12] };
    }

    @Override
    protected void parametersInstantiation_EM() {
        super.parametersInstantiation_EM();
        Parameters.Group param_0 = parametrization.getGroup(0, false);
        fuelConsumptionParameter = param_0.makeInParameter(
                0,
                1,
                FUEL_CONSUMPTION_PARAM_NAME,
                FUEL_CONSUMPTION_VALUE);
    }

    // Fuel consumption parameter localisation
    private static final INameFunction<GT_MetaTileEntity_EM_ForgeOfGods> FUEL_CONSUMPTION_PARAM_NAME = (base,
            p) -> translateToLocal("gt.blockmachines.multimachine.FOG.fuelconsumption");
    // Fuel consumption parameter value
    private static final IStatusFunction<GT_MetaTileEntity_EM_ForgeOfGods> FUEL_CONSUMPTION_VALUE = (base,
            p) -> LedStatus.fromLimitsInclusiveOuterBoundary(p.get(), 0, 0, 10, 10);

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        structureBuild_EM(STRUCTURE_PIECE_MAIN, 31, 34, 0, stackSize, hintsOnly);
    }

    private final Map<FluidStack, Integer> validFuelMap = new HashMap<FluidStack, Integer>() {

        {
            put(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(1), 2500);
            put(MaterialsUEVplus.WhiteDwarfMatter.getMolten(1), 5);
            put(MaterialsUEVplus.BlackDwarfMatter.getMolten(1), 3);
        }
    };

    public boolean addFuelInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) return false;
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch_Input) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            ((GT_MetaTileEntity_Hatch_Input) aMetaTileEntity).mRecipeMap = null;
            fuelInputHatch = (GT_MetaTileEntity_Hatch_Input) aMetaTileEntity;
            return true;
        }
        return false;
    }

    private int ticker = 0;

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        if (!super.onRunningTick(aStack)) {
            criticalStopMachine();
            return false;
        }

        if (ticker % 100 == 0) {
            if (fuelInputHatch == null) {
                criticalStopMachine();
                return false;
            }
            FluidStack fluidInHatch = fuelInputHatch.getFluid();
            // Iterate over valid fluids and drain them
            for (FluidStack validFluid : validFuelMap.keySet()) {
                int drainAmount = (int) (validFuelMap.get(validFluid) * fuelConsumptionParameter.get());
                if (fluidInHatch.isFluidEqual(validFluid)) {
                    FluidStack tFluid = new FluidStack(validFluid, drainAmount);
                    FluidStack tLiquid = fuelInputHatch.drain(tFluid.amount, true);
                    if (tLiquid == null || tLiquid.amount < tFluid.amount) {
                        criticalStopMachine();
                    }
                }
            }
            ticker = 0;
        }
        ticker++;

        return true;
    }

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {

        spacetimeCompressionFieldMetadata = -1;
        fuelInputHatch = null;

        // Check structure of multi.
        if (!structureCheck_EM(STRUCTURE_PIECE_MAIN, 31, 34, 0)) {
            return false;
        }

        // Check if there is 1 output bus, and it is a ME output bus.
        {
            if (mOutputBusses.size() != 1) {
                return false;
            }

            if (!(mOutputBusses.get(0) instanceof GT_MetaTileEntity_Hatch_OutputBus_ME)) {
                return false;
            }
        }

        // Check if there is 1 output hatch, and they are ME output hatches.
        {
            if (mOutputHatches.size() != 1) {
                return false;
            }

            if (!(mOutputHatches.get(0) instanceof GT_MetaTileEntity_Hatch_Output_ME)) {
                return false;
            }
        }

        // Check there is 1 input bus.
        if (mInputBusses.size() != 1) {
            return false;
        }

        // Make sure there are no energy hatches.
        {
            if (mEnergyHatches.size() > 0) {
                return false;
            }

            if (mExoticEnergyHatches.size() > 0) {
                return false;
            }
        }

        // Make sure there are 2 input hatches.
        if (mInputHatches.size() != 1) {
            return false;
        }

        if (fuelInputHatch == null) {
            return false;
        }

        mHardHammer = true;
        mSoftHammer = true;
        mScrewdriver = true;
        mCrowbar = true;
        mSolderingTool = true;
        mWrench = true;
        return true;
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
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide()) {
            // Connect modules
            if (getBaseMetaTileEntity().isAllowedToWork()) {
                if (aTick % MODULE_CHECK_INTERVAL == 0) {
                    if (moduleHatches.size() > 0) {
                        for (GT_MetaTileEntity_EM_BaseModule module : moduleHatches) {
                            module.connect();
                        }
                    }
                }
            } else {
                if (moduleHatches.size() > 0) {
                    for (GT_MetaTileEntity_EM_BaseModule module : moduleHatches) {
                        module.disconnect();
                    }
                }
            }
            if (mEfficiency < 0) mEfficiency = 0;
            fixAllMaintenance();
        }
    }

    public boolean addModuleToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        }
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) {
            return false;
        }
        if (aMetaTileEntity instanceof GT_MetaTileEntity_EM_BaseModule) {
            return moduleHatches.add((GT_MetaTileEntity_EM_BaseModule) aMetaTileEntity);
        }
        return false;
    }

    public enum moduleElement implements IHatchElement<GT_MetaTileEntity_EM_ForgeOfGods> {

        Module(GT_MetaTileEntity_EM_ForgeOfGods::addModuleToMachineList, GT_MetaTileEntity_EM_BaseModule.class) {

            @Override
            public long count(GT_MetaTileEntity_EM_ForgeOfGods tileEntity) {
                return tileEntity.moduleHatches.size();
            }
        };

        private final List<Class<? extends IMetaTileEntity>> mteClasses;
        private final IGT_HatchAdder<GT_MetaTileEntity_EM_ForgeOfGods> adder;

        @SafeVarargs
        moduleElement(IGT_HatchAdder<GT_MetaTileEntity_EM_ForgeOfGods> adder,
                Class<? extends IMetaTileEntity>... mteClasses) {
            this.mteClasses = Collections.unmodifiableList(Arrays.asList(mteClasses));
            this.adder = adder;
        }

        @Override
        public List<? extends Class<? extends IMetaTileEntity>> mteClasses() {
            return mteClasses;
        }

        public IGT_HatchAdder<? super GT_MetaTileEntity_EM_ForgeOfGods> adder() {
            return adder;
        }
    }

    protected boolean inputSeparation = false;
    protected static String INPUT_SEPARATION_NBT_KEY = "inputSeparation";

    @Override
    public String[] getInfoData() {
        ArrayList<String> str = new ArrayList<>(Arrays.asList(super.getInfoData()));
        str.add("Output Buses:" + formatNumbers(mOutputBusses.size()));
        str.add("Output Hatches:" + formatNumbers(mOutputHatches.size()));
        str.add("Input Buses:" + formatNumbers(mInputBusses.size()));
        str.add("Input Hatches:" + formatNumbers(mInputHatches.size()));
        str.add("Max Parallel:" + formatNumbers(Math.pow(4, spacetimeCompressionFieldMetadata + 1)));
        return str.toArray(new String[0]);
    }

    @Override
    public final void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        inputSeparation = !inputSeparation;
        GT_Utility.sendChatToPlayer(
                aPlayer,
                StatCollector.translateToLocal("GT5U.machines.separatebus") + " " + inputSeparation);
    }

    @Override
    public void onRemoval() {
        if (moduleHatches != null && moduleHatches.size() > 0) {
            for (GT_MetaTileEntity_EM_BaseModule module : moduleHatches) {
                module.disconnect();
            }
        }
        super.onRemoval();
    }

    protected void fixAllMaintenance() {
        mWrench = true;
        mScrewdriver = true;
        mSoftHammer = true;
        mHardHammer = true;
        mSolderingTool = true;
        mCrowbar = true;
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        spacetimeCompressionFieldMetadata = aNBT.getInteger("spacetimeCompressionTier") - 1;
        solenoidCoilMetadata = aNBT.getInteger("solenoidCoilTier") + 7;
        super.loadNBTData(aNBT);
        if (!aNBT.hasKey(INPUT_SEPARATION_NBT_KEY)) {
            inputSeparation = aNBT.getBoolean("separateBusses");
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        aNBT.setInteger("spacetimeCompressionTier", spacetimeCompressionFieldMetadata + 1);
        aNBT.setInteger("solenoidCoilTier", solenoidCoilMetadata - 7);
        super.saveNBTData(aNBT);
    }

    public static int getMaxParallels() {
        return (int) Math.pow(4, spacetimeCompressionFieldMetadata + 1);
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sBlastRecipes;
    }

    @Override
    public boolean energyFlowOnRunningTick(ItemStack aStack, boolean allowProduction) {
        return true;
    }

    @Override
    public String[] getStructureDescription(ItemStack stackSize) {
        return new String[] { "Forge of Gods multiblock" };
    }
}
