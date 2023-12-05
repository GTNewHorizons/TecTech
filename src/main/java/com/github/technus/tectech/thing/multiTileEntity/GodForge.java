package com.github.technus.tectech.thing.multiTileEntity;

import static com.github.technus.tectech.thing.casing.TT_Container_Casings.sBlockCasingsTT;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.multitileentity.multiblock.base.MultiBlockPart.*;

import net.minecraft.item.ItemStack;

import org.apache.commons.lang3.tuple.Pair;

import com.github.technus.tectech.thing.casing.TT_Container_Casings;
import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import com.gtnewhorizon.structurelib.util.Vec3Impl;

import gregtech.api.GregTech_API;
import gregtech.api.logic.ComplexParallelProcessingLogic;
import gregtech.api.multitileentity.multiblock.base.ComplexParallelController;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;

public class GodForge extends ComplexParallelController<GodForge> {

    private static IStructureDefinition<GodForge> STRUCTURE_DEFINITION = null;
    protected static final String BASE_STRUCTURE = "Main";
    protected static final int MAX_PROCESSES = 4;
    protected int numberOfProcessors = MAX_PROCESSES;
    protected static final Vec3Impl OFFSET = new Vec3Impl(0, 1, 0);
    private int solenoidCoilMetadata = -1;
    private int spacetimeCompressionFieldMetadata = -1;

    public GodForge() {
        super();
        processingLogic = new ComplexParallelProcessingLogic(RecipeMaps.chemicalReactorRecipes, MAX_PROCESSES);
    }

    @Override
    public short getCasingRegistryID() {
        return 0;
    }

    @Override
    public int getCasingMeta() {
        return 5000;
    }

    @Override
    public Vec3Impl getStartingStructureOffset() {
        return OFFSET;
    }

    @Override
    public boolean checkMachine() {
        setMaxComplexParallels(MAX_PROCESSES);
        buildState.startBuilding(getStartingStructureOffset());
        return checkPiece(BASE_STRUCTURE, buildState.stopBuilding());
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildState.startBuilding(getStartingStructureOffset());
        buildPiece(BASE_STRUCTURE, trigger, hintsOnly, buildState.stopBuilding());
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int elementBudget, ISurvivalBuildEnvironment env) {
        buildState.startBuilding(getStartingStructureOffset());
        return survivalBuildPiece(BASE_STRUCTURE, trigger, buildState.stopBuilding(), elementBudget, env, false);
    }

    @Override
    public IStructureDefinition<GodForge> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<GodForge>builder()
                    .addShape(BASE_STRUCTURE, transpose(new String[][] { { "ABCDEF" }, { "~ABCDEF" }, { "ABCDEF" } }

                    ))
                    .addElement(
                            'A',
                            addMultiTileCasing(
                                    "gt.multitileentity.casings",
                                    getCasingMeta(),
                                    FLUID_IN | ITEM_IN | FLUID_OUT | ITEM_OUT))
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
                                    (block, meta) -> block == TT_Container_Casings.SpacetimeCompressionFieldGenerators
                                            ? meta
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
                                    (t, meta) -> t.spacetimeCompressionFieldMetadata = meta,
                                    t -> t.spacetimeCompressionFieldMetadata))
                    .addElement('F', addMultiTileCasing("gt.multitileentity.casings", getCasingMeta(), FLUID_IN))
                    .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public String getTileEntityName() {
        return "gt.multitileentity.multiblock.godforge";
    }

    @Override
    public String getLocalName() {
        return "Forge of Gods";
    }

    @Override
    public GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Forge of Gods").addInfo("this is a multi").addCasingInfoExactly("multi things", 3, true)
                .addCasingInfoExactly("whatever", 2, false).toolTipFinisher("Gregtech");
        return tt;
    }

}
