package com.github.technus.tectech.proxy;

import static com.github.technus.tectech.TecTech.RANDOM;

import com.github.technus.tectech.Reference;
import com.github.technus.tectech.compatibility.openmodularturrets.TT_turret_loader;
import com.github.technus.tectech.thing.block.*;
import com.github.technus.tectech.thing.item.DebugElementalInstanceContainer_EM;
import com.github.technus.tectech.thing.item.ElementalDefinitionContainer_EM;
import com.github.technus.tectech.thing.item.renderElemental.RenderElementalName;
import com.gtnewhorizon.structurelib.entity.fx.WeightlessParticleFX;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderInfo() {
        QuantumGlassBlock.renderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(QuantumGlassBlock.renderID, new QuantumGlassRender());

        //        QuantumStuffBlock.renderID = RenderingRegistry.getNextAvailableRenderId();
        //        RenderingRegistry.registerBlockHandler(QuantumStuffBlock.renderID, new QuantumStuffRender());

        MinecraftForgeClient.registerItemRenderer(
                ElementalDefinitionContainer_EM.INSTANCE, RenderElementalName.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(
                DebugElementalInstanceContainer_EM.INSTANCE, RenderElementalName.INSTANCE);
        // MinecraftForgeClient.registerItemRenderer(ElementalDefinitionScanStorage_EM.INSTANCE,
        // RenderElementalName.INSTANCE);

        if (Loader.isModLoaded("openmodularturrets")) {
            new TT_turret_loader().run();
        }

        ClientRegistry.bindTileEntitySpecialRenderer(TileEyeOfHarmony.class, new RenderEyeOfHarmony());
    }

    @Override
    public void em_particle(IGregTechTileEntity aMuffler, byte facing) { // CUTE!
        ForgeDirection aDir = ForgeDirection.getOrientation(facing);
        float xPos = aDir.offsetX * 0.76F + aMuffler.getXCoord() + 0.25F;
        float yPos = aDir.offsetY * 0.76F + aMuffler.getYCoord() + 0.25F;
        float zPos = aDir.offsetZ * 0.76F + aMuffler.getZCoord() + 0.25F;

        EntityFX particle = new WeightlessParticleFX(
                aMuffler.getWorld(),
                xPos + RANDOM.nextFloat() * 0.5F,
                yPos + RANDOM.nextFloat() * 0.5F,
                zPos + RANDOM.nextFloat() * 0.5F,
                0,
                0,
                0);
        particle.setRBGColorF(0, 0.6F * RANDOM.nextFloat(), 0.8f);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    @Override
    public void pollutor_particle(IGregTechTileEntity aMuffler, byte facing) {
        ForgeDirection aDir = ForgeDirection.getOrientation(facing);
        float xPos = aDir.offsetX * 0.76F + aMuffler.getXCoord() + 0.25F;
        float yPos = aDir.offsetY * 0.76F + aMuffler.getYCoord() + 0.25F;
        float zPos = aDir.offsetZ * 0.76F + aMuffler.getZCoord() + 0.25F;

        float ySpd = aDir.offsetY * 0.1F + 0.2F + 0.1F * (float) RANDOM.nextGaussian();
        float xSpd;
        float zSpd;

        if (aDir.offsetY == -1) {
            float temp = RANDOM.nextFloat() * 2 * (float) Math.PI;
            xSpd = (float) Math.sin(temp) * 0.1F * (float) RANDOM.nextGaussian();
            zSpd = (float) Math.cos(temp) * 0.1F * (float) RANDOM.nextGaussian();
        } else {
            xSpd = aDir.offsetX * (0.1F + 0.2F * (float) RANDOM.nextGaussian());
            zSpd = aDir.offsetZ * (0.1F + 0.2F * (float) RANDOM.nextGaussian());
        }
        aMuffler.getWorld()
                .spawnParticle(
                        "largesmoke",
                        xPos + RANDOM.nextFloat() * 0.5F,
                        yPos + RANDOM.nextFloat() * 0.5F,
                        zPos + RANDOM.nextFloat() * 0.5F,
                        xSpd,
                        ySpd,
                        zSpd);
        aMuffler.getWorld()
                .spawnParticle(
                        "largesmoke",
                        xPos + RANDOM.nextFloat() * 0.5F,
                        yPos + RANDOM.nextFloat() * 0.5F,
                        zPos + RANDOM.nextFloat() * 0.5F,
                        xSpd,
                        ySpd,
                        zSpd);
        aMuffler.getWorld()
                .spawnParticle(
                        "largesmoke",
                        xPos + RANDOM.nextFloat() * 0.5F,
                        yPos + RANDOM.nextFloat() * 0.5F,
                        zPos + RANDOM.nextFloat() * 0.5F,
                        xSpd,
                        ySpd,
                        zSpd);
    }

    @Override
    public void em_particle(World w, double x, double y, double z) { // CUTE!
        EntityFX particle = new WeightlessParticleFX(
                w,
                x + RANDOM.nextFloat() * 0.5F,
                y + RANDOM.nextFloat() * 0.5F,
                z + RANDOM.nextFloat() * 0.5F,
                0,
                0,
                0);
        particle.setRBGColorF(0, 0.6F * RANDOM.nextFloat(), 0.8f);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    @Override
    public void pollutor_particle(World w, double x, double y, double z) {
        w.spawnParticle(
                "largesmoke",
                x + RANDOM.nextFloat() * 0.5F,
                y + RANDOM.nextFloat() * 0.5F,
                z + RANDOM.nextFloat() * 0.5F,
                0,
                0,
                0);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    @Override
    public void printInchat(String... strings) {
        GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        for (String s : strings) {
            chat.printChatMessage(new ChatComponentText(s));
        }
    }

    @Override
    public void playSound(IGregTechTileEntity base, String name) {
        base.getWorld()
                .playSoundEffect(
                        base.getXCoord(), base.getYCoord(), base.getZCoord(), Reference.MODID + ':' + name, 1, 1);
    }

    @Override
    public void renderAABB(World w, AxisAlignedBB box) {
        em_particle(w, box.minX, box.minY, box.minZ);
        em_particle(w, box.minX, box.minY, box.maxZ);
        em_particle(w, box.minX, box.maxY, box.maxZ);
        em_particle(w, box.minX, box.maxY, box.minZ);
        em_particle(w, box.maxX, box.maxY, box.minZ);
        em_particle(w, box.maxX, box.maxY, box.maxZ);
        em_particle(w, box.maxX, box.minY, box.maxZ);
        em_particle(w, box.maxX, box.minY, box.minZ);
    }

    @Override
    public EntityClientPlayerMP getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public boolean isThePlayer(EntityPlayer player) {
        return getPlayer() == player;
    }
}
