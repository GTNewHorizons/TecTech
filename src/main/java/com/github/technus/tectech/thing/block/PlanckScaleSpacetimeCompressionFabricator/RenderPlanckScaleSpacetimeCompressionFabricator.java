package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import static com.github.technus.tectech.thing.item.ContainerItem.EOH_RenderingUtils.renderBlockInWorld;


public class RenderPlanckScaleSpacetimeCompressionFabricator extends TileEntitySpecialRenderer {

    public RenderPlanckScaleSpacetimeCompressionFabricator() { }

    //private static final String[] testShape =      {"   BBB           BBB      BBB           BBB      BBB           BBB      BBB           BBB   "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB ","BBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBB","BBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBB","BBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBB"," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB ","   BBB     B     BBB      BBB     B     BBB      BBB     B     BBB      BBB     B     BBB   ","    B     BBB     B        B     BBB     B        B     BBB     B        B     BBB     B    ","    BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB    ","     BBBBBAAABBBBB          BBBBBAAABBBBB          BBBBBAAABBBBB          BBBBBAAABBBBB     ","    BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB    ","    B     BBB     B        B     BBB     B        B     BBB     B        B     BBB     B    ","   BBB     B     BBB      BBB     B     BBB      BBB     B     BBB      BBB     B     BBB   "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB ","BBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBB","BBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBB","BBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBB"," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB ","   BBB           BBB      BBB           BBB      BBB           BBB      BBB           BBB   ","   BBB           BBB      BBB           BBB      BBB           BBB      BBB           BBB   "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB ","BBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBB","BBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBB","BBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBB"," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB ","   BBB     B     BBB      BBB     B     BBB      BBB     B     BBB      BBB     B     BBB   ","    B     BBB     B        B     BBB     B        B     BBB     B        B     BBB     B    ","    BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB    ","     BBBBBAAABBBBB          BBBBBAAABBBBB          BBBBBAAABBBBB          BBBBBAAABBBBB     ","    BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB    ","    B     BBB     B        B     BBB     B        B     BBB     B        B     BBB     B    ","   BBB     B     BBB      BBB     B     BBB      BBB     B     BBB      BBB     B     BBB   "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB ","BBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBB","BBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBB","BBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBB"," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB ","   BBB           BBB      BBB           BBB      BBB           BBB      BBB           BBB   ","   BBB           BBB      BBB           BBB      BBB           BBB      BBB           BBB   "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB ","BBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBB","BBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBB","BBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBB"," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB ","   BBB     B     BBB      BBB     B     BBB      BBB     B     BBB      BBB     B     BBB   ","    B     BBB     B        B     BBB     B        B     BBB     B        B     BBB     B    ","    BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB    ","     BBBBBAAABBBBB          BBBBBAAABBBBB          BBBBBAAABBBBB          BBBBBAAABBBBB     ","    BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB    ","    B     BBB     B        B     BBB     B        B     BBB     B        B     BBB     B    ","   BBB     B     BBB      BBB     B     BBB      BBB     B     BBB      BBB     B     BBB   "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB ","BBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBB","BBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBB","BBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBB"," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB ","   BBB           BBB      BBB           BBB      BBB           BBB      BBB           BBB   ","   BBB           BBB      BBB           BBB      BBB           BBB      BBB           BBB   "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB ","BBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBB","BBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBB","BBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBB"," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB ","   BBB     B     BBB      BBB     B     BBB      BBB     B     BBB      BBB     B     BBB   ","    B     BBB     B        B     BBB     B        B     BBB     B        B     BBB     B    ","    BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB    ","     BBBBBAAABBBBB          BBBBBAAABBBBB          BBBBBAAABBBBB          BBBBBAAABBBBB     ","    BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB    ","    B     BBB     B        B     BBB     B        B     BBB     B        B     BBB     B    ","   BBB     B     BBB      BBB     B     BBB      BBB     B     BBB      BBB     B     BBB   "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB ","BBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBB","BBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBB","BBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBB"," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB ","   BBB           BBB      BBB           BBB      BBB           BBB      BBB           BBB   ","   BBB           BBB      BBB           BBB      BBB           BBB      BBB           BBB   "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB ","BBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBB","BBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBB","BBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBB"," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB ","   BBB     B     BBB      BBB     B     BBB      BBB     B     BBB      BBB     B     BBB   ","    B     BBB     B        B     BBB     B        B     BBB     B        B     BBB     B    ","    BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB    ","     BBBBBAAABBBBB          BBBBBAAABBBBB          BBBBBAAABBBBB          BBBBBAAABBBBB     ","    BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB    ","    B     BBB     B        B     BBB     B        B     BBB     B        B     BBB     B    ","   BBB     B     BBB      BBB     B     BBB      BBB     B     BBB      BBB     B     BBB   "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB ","BBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBB","BBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBB","BBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBB"," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB ","   BBB           BBB      BBB           BBB      BBB           BBB      BBB           BBB   ","   BBB           BBB      BBB           BBB      BBB           BBB      BBB           BBB   "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB ","BBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBB","BBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBB","BBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBB"," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB ","   BBB     B     BBB      BBB     B     BBB      BBB     B     BBB      BBB     B     BBB   ","    B     BBB     B        B     BBB     B        B     BBB     B        B     BBB     B    ","    BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB    ","     BBBBBAAABBBBB          BBBBBAAABBBBB          BBBBBAAABBBBB          BBBBBAAABBBBB     ","    BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB        BB   BBABB   BB    ","    B     BBB     B        B     BBB     B        B     BBB     B        B     BBB     B    ","   BBB     B     BBB      BBB     B     BBB      BBB     B     BBB      BBB     B     BBB   "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB "," BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB  BBBBBBB   B   BBBBBBB ","BBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBBBBBAAABBB BBB BBBAAABBB","BBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBBBBBAAABBBBB BBBBBAAABBB","BBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBBBBBAAABBB     BBBAAABBB"," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB "," BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB  BBBBBBB       BBBBBBB ","   BBB           BBB      BBB           BBB      BBB           BBB      BBB           BBB   "};
    @SuppressWarnings("SpellCheckingInspection")
    private static final String[][] testShape = new String[][] { { "                                 ",
            "         N   N     N   N         ", "         N   N     N   N         ", "         N   N     N   N         ",
            "                                 ", "                                 ", "                                 ",
            "         N   N     N   N         ", "         N   N     N   N         ", " NNN   NNN   N     N   NNN   NNN ",
            "                                 ", "                                 ", "                                 ",
            " NNN   NNN             NNN   NNN ", "                                 ", "                                 ",
            "                                 ", "                                 ", "                                 ",
            " NNN   NNN             NNN   NNN ", "                                 ", "                                 ",
            "                                 ", " NNN   NNN             NNN   NNN " },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ",
                    "         bCCCb     bCCCb         ", "         N   N     N   N         ",
                    "                                 ", "         N   N     N   N         ",
                    "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ",
                    "NbbbN NbbNCCCb     bCCCNbbN NbbbN", " CCC   CCC   N     N   CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    "NbbbN NbbbN           NbbbN NbbbN", "  N     N               N     N  ",
                    "  N     N               N     N  ", "                                 ",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "NbbbN NbbbN           NbbbN NbbbN", " CCC   CCC             CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    "NbbbN NbbbN    N N    NbbbN NbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ", "      NNNbbbbbNNsNNbbbbbNNN      ",
                    "    ss   bCCCb     bCCCb   ss    ", "   s     N   N     N   N     s   ",
                    "   s                         s   ", "  N      N   N     N   N      N  ",
                    "  N      bCCCb     bCCCb      N  ", "  N     sbbbbbNNsNNbbbbbs     N  ",
                    "NbbbN NbbNCCCb     bCCCNbbN NbbbN", " CbC   CbC   N     N   CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    "NbbbN NbbbN           NbbbN NbbbN", " NNN   NNN             NNN   NNN ",
                    " NNN   NNN             NNN   NNN ", "  s     s               s     s  ",
                    " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ",
                    "NbbbN NbbbN           NbbbN NbbbN", " CbC   CbC             CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    "NbbbN NbbbNNNNNsNsNNNNNbbbN NbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ", "    ss   bCCCb     bCCCb   ss    ",
                    "         bCCCb     bCCCb         ", "  s      NCCCN     NCCCN      s  ",
                    "  s      NCCCN     NCCCN      s  ", "         NCCCN     NCCCN         ",
                    "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ",
                    "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", " CCCCCCCCC   N     N   CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    "NbbbNNNbbbN           NbbbNNNbbbN", "  N     N               N     N  ",
                    "  N     N               N     N  ", "                                 ",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "NbbbNNNbbbN           NbbbNNNbbbN", " CCCCCCCCC             CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    "NbbbNNNbbbN    NbN    NbbbNNNbbbN", },
            { "                                 ", "         N   N     N   N         ", "   s     N   N     N   N     s   ",
                    "  s      NCCCN     NCCCN      s  ", "                                 ",
                    "                                 ", "                                 ",
                    "         NCCCN     NCCCN         ", "         N   N     N   N         ",
                    " NNN   NN    N     N    NN   NNN ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " NNN   NNN             NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN             NNN   NNN ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " NNN   NNN     NbN     NNN   NNN ", },
            { "                                 ", "                                 ", "   s                         s   ",
                    "  s      NCCCN     NCCCN      s  ", "                                 ",
                    "                                 ", "                                 ",
                    "         NCCCN     NCCCN         ", "                                 ",
                    "   N   N                 N   N   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   N   N                 N   N   ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "   N   N                 N   N   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   N   N       NbN       N   N   ", },
            { "                                 ", "         N   N     N   N         ", "  N      N   N     N   N      N  ",
                    "         NCCCN     NCCCN         ", "                                 ",
                    "                                 ", "                                 ",
                    "         NCCCN     NCCCN         ", "         N   N     N   N         ",
                    " NNN   NN    N     N    NN   NNN ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " NNN   NNN             NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN             NNN   NNN ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " NNN   NNN     NbN     NNN   NNN ", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ", "  N      bCCCb     bCCCb      N  ",
                    "         bCCCb     bCCCb         ", "         NCCCN     NCCCN         ",
                    "         NCCCN     NCCCN         ", "         NCCCN     NCCCN         ",
                    "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ",
                    "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", " CCCCCCCCC   N     N   CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    "NbbbNNNbbbN           NbbbNNNbbbN", "  N     N               N     N  ",
                    "  N     N               N     N  ", "                                 ",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "NbbbNNNbbbN           NbbbNNNbbbN", " CCCCCCCCC             CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    "NbbbNNNbbbN    NbN    NbbbNNNbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ", "  N     sbbbbbNNsNNbbbbbs     N  ",
                    "         bCCCb     bCCCb         ", "         N   N     N   N         ",
                    "                                 ", "         N   N     N   N         ",
                    "         bCCCb     bCCCb         ", "  s     sbbbbbNNsNNbbbbbs     s  ",
                    "NbbbN NbbNCCCb     bCCCNbbN NbbbN", " CbC   CbC   N     N   CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    "NbbbN NbbbN           NbbbN NbbbN", " NNN   NNN             NNN   NNN ",
                    " NNN   NNN             NNN   NNN ", "  s     s               s     s  ",
                    " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ",
                    "NbbbN NbbbN           NbbbN NbbbN", " CbC   CbC             CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    "NbbbN NbbbNNNNNsNsNNNNNbbbN NbbbN", },
            { " NNN   NNN   N     N   NNN   NNN ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "NbbbN NbbNCCCb     bCCCNbbN NbbbN",
                    "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", " NNN   NNN   N     N   NNN   NNN ",
                    "   N   N                 N   N   ", " NNN   NNN   N     N   NNN   NNN ",
                    "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", "NbbbN NbbNCCCb     bCCCNbbN NbbbN",
                    "NNNN   NNNCCCb     bCCCNNN   NNNN", " CCC   CCC   N     N   CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    "NbbbN NbbbN           NbbbN NbbbN", "  N     N               N     N  ",
                    "  N     N               N     N  ", "                                 ",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "NbbbN NbbbN           NbbbN NbbbN", " CCC   CCC             CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    "NbbbN NbbbN    NbN    NbbbN NbbbN", },
            { "                                 ", " CCC   CCC   N     N   CCC   CCC ", " CbC   CbC   N     N   CbC   CbC ",
                    " CCCCCCCCC   N     N   CCCCCCCCC ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " CCCCCCCCC   N     N   CCCCCCCCC ", " CbC   CbC   N     N   CbC   CbC ",
                    " CCC   CCC   N     N   CCC   CCC ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN             NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN             NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN     NbN     NNN   NNN ", },
            { "                                 ", " CCC   CCC             CCC   CCC ", " CbC   CbC             CbC   CbC ",
                    " CCCCCCCCC             CCCCCCCCC ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " CCCCCCCCC             CCCCCCCCC ", " CbC   CbC             CbC   CbC ",
                    " CCC   CCC             CCC   CCC ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  N     N      NbN      N     N  ", },
            { "                                 ", " CCC   CCC             CCC   CCC ", " CbC   CbC             CbC   CbC ",
                    " CCCCCCCCC             CCCCCCCCC ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " CCCCCCCCC             CCCCCCCCC ", " CbC   CbC             CbC   CbC ",
                    " CCC   CCC             CCC   CCC ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  N     N      NbN      N     N  ", },
            { " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN", "NbbbN NbbbN           NbbbN NbbbN",
                    "NbbbNNNbbbN           NbbbNNNbbbN", " NNN   NNN             NNN   NNN ",
                    "   N   N                 N   N   ", " NNN   NNN             NNN   NNN ",
                    "NbbbNNNbbbN           NbbbNNNbbbN", "NbbbN NbbbN           NbbbN NbbbN",
                    "NbbbN NbbbN           NbbbN NbbbN", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  N     N     NsNsN     N     N  ", },
            { "                                 ", "                                 ", "  N     N               N     N  ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  N     N               N     N  ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  N     N    NbbbbbN    N     N  ", },
            { "                                 ", "                                 ", "  N     N               N     N  ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  N     N               N     N  ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                N                ",
                    " NsNNNNNsNNNNsbbbbbsNNNNsNNNNNsN ", },
            { "                                 ", "                                 ", "  s     s               s     s  ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  s     s               s     s  ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                ~                ", "               NNN               ",
                    "  NbbbbbNbbbbNbbbbbNbbbbNbbbbbN  ", },
            { "                                 ", "                                 ", "  N     N               N     N  ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  N     N               N     N  ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                N                ",
                    " NsNNNNNsNNNNsbbbbbsNNNNsNNNNNsN ", },
            { "                                 ", "                                 ", "  N     N               N     N  ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  N     N               N     N  ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  N     N    NbbbbbN    N     N  ", },
            { " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN", "NbbbN NbbbN           NbbbN NbbbN",
                    "NbbbNNNbbbN           NbbbNNNbbbN", " NNN   NNN             NNN   NNN ",
                    "   N   N                 N   N   ", " NNN   NNN             NNN   NNN ",
                    "NbbbNNNbbbN           NbbbNNNbbbN", "NbbbN NbbbN           NbbbN NbbbN",
                    "NbbbN NbbbN           NbbbN NbbbN", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  N     N     NsNsN     N     N  ", },
            { "                                 ", " CCC   CCC             CCC   CCC ", " CbC   CbC             CbC   CbC ",
                    " CCCCCCCCC             CCCCCCCCC ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " CCCCCCCCC             CCCCCCCCC ", " CbC   CbC             CbC   CbC ",
                    " CCC   CCC             CCC   CCC ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  N     N      NbN      N     N  ", },
            { "                                 ", " CCC   CCC             CCC   CCC ", " CbC   CbC             CbC   CbC ",
                    " CCCCCCCCC             CCCCCCCCC ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " CCCCCCCCC             CCCCCCCCC ", " CbC   CbC             CbC   CbC ",
                    " CCC   CCC             CCC   CCC ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  N     N      NbN      N     N  ", },
            { "                                 ", " CCC   CCC   N     N   CCC   CCC ", " CbC   CbC   N     N   CbC   CbC ",
                    " CCCCCCCCC   N     N   CCCCCCCCC ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " CCCCCCCCC   N     N   CCCCCCCCC ", " CbC   CbC   N     N   CbC   CbC ",
                    " CCC   CCC   N     N   CCC   CCC ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN             NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN             NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN     NbN     NNN   NNN ", },
            { " NNN   NNN   N     N   NNN   NNN ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "NbbbN NbbNCCCb     bCCCNbbN NbbbN",
                    "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", " NNN   NNN   N     N   NNN   NNN ",
                    "   N   N                 N   N   ", " NNN   NNN   N     N   NNN   NNN ",
                    "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", "NbbbN NbbNCCCb     bCCCNbbN NbbbN",
                    "NNNN   NNNCCCb     bCCCNNN   NNNN", " CCC   CCC   N     N   CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    "NbbbN NbbbN           NbbbN NbbbN", "  N     N               N     N  ",
                    "  N     N               N     N  ", "                                 ",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "NbbbN NbbbN           NbbbN NbbbN", " CCC   CCC             CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    "NbbbN NbbbN    NbN    NbbbN NbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ", "  N     sbbbbbNNsNNbbbbbs     N  ",
                    "         bCCCb     bCCCb         ", "         N   N     N   N         ",
                    "                                 ", "         N   N     N   N         ",
                    "         bCCCb     bCCCb         ", "  s     sbbbbbNNsNNbbbbbs     s  ",
                    "NbbbN NbbNCCCb     bCCCNbbN NbbbN", " CbC   CbC   N     N   CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    "NbbbN NbbbN           NbbbN NbbbN", " NNN   NNN             NNN   NNN ",
                    " NNN   NNN             NNN   NNN ", "  s     s               s     s  ",
                    " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ",
                    "NbbbN NbbbN           NbbbN NbbbN", " CbC   CbC             CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    "NbbbN NbbbNNNNNsNsNNNNNbbbN NbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ", "  N      bCCCb     bCCCb      N  ",
                    "         bCCCb     bCCCb         ", "         NCCCN     NCCCN         ",
                    "         NCCCN     NCCCN         ", "         NCCCN     NCCCN         ",
                    "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ",
                    "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", " CCCCCCCCC   N     N   CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    "NbbbNNNbbbN           NbbbNNNbbbN", "  N     N               N     N  ",
                    "  N     N               N     N  ", "                                 ",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "NbbbNNNbbbN           NbbbNNNbbbN", " CCCCCCCCC             CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    "NbbbNNNbbbN    NbN    NbbbNNNbbbN", },
            { "                                 ", "         N   N     N   N         ", "  N      N   N     N   N      N  ",
                    "         NCCCN     NCCCN         ", "                                 ",
                    "                                 ", "                                 ",
                    "         NCCCN     NCCCN         ", "         N   N     N   N         ",
                    " NNN   NN    N     N    NN   NNN ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " NNN   NNN             NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN             NNN   NNN ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " NNN   NNN     NbN     NNN   NNN ", },
            { "                                 ", "                                 ", "   s                         s   ",
                    "  s      NCCCN     NCCCN      s  ", "                                 ",
                    "                                 ", "                                 ",
                    "         NCCCN     NCCCN         ", "                                 ",
                    "   N   N                 N   N   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   N   N                 N   N   ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "   N   N                 N   N   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   N   N       NbN       N   N   ", },
            { "                                 ", "         N   N     N   N         ", "   s     N   N     N   N     s   ",
                    "  s      NCCCN     NCCCN      s  ", "                                 ",
                    "                                 ", "                                 ",
                    "         NCCCN     NCCCN         ", "         N   N     N   N         ",
                    " NNN   NN    N     N    NN   NNN ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " NNN   NNN             NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN             NNN   NNN ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    " NNN   NNN     NbN     NNN   NNN ", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ", "    ss   bCCCb     bCCCb   ss    ",
                    "         bCCCb     bCCCb         ", "  s      NCCCN     NCCCN      s  ",
                    "  s      NCCCN     NCCCN      s  ", "         NCCCN     NCCCN         ",
                    "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ",
                    "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN", " CCCCCCCCC   N     N   CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    "NbbbNNNbbbN           NbbbNNNbbbN", "  N     N               N     N  ",
                    "  N     N               N     N  ", "                                 ",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "NbbbNNNbbbN           NbbbNNNbbbN", " CCCCCCCCC             CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    "NbbbNNNbbbN    NbN    NbbbNNNbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ", "      NNNbbbbbNNsNNbbbbbNNN      ",
                    "    ss   bCCCb     bCCCb   ss    ", "   s     N   N     N   N     s   ",
                    "   s                         s   ", "  N      N   N     N   N      N  ",
                    "  N      bCCCb     bCCCb      N  ", "  N     sbbbbbNNsNNbbbbbs     N  ",
                    "NbbbN NbbNCCCb     bCCCNbbN NbbbN", " CbC   CbC   N     N   CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    "NbbbN NbbbN           NbbbN NbbbN", " NNN   NNN             NNN   NNN ",
                    " NNN   NNN             NNN   NNN ", "  s     s               s     s  ",
                    " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ",
                    "NbbbN NbbbN           NbbbN NbbbN", " CbC   CbC             CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    "NbbbN NbbbNNNNNsNsNNNNNbbbN NbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ",
                    "         bCCCb     bCCCb         ", "         N   N     N   N         ",
                    "                                 ", "         N   N     N   N         ",
                    "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ",
                    "NbbbN NbbNCCCb     bCCCNbbN NbbbN", " CCC   CCC   N     N   CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    "NbbbN NbbbN           NbbbN NbbbN", "  N     N               N     N  ",
                    "  N     N               N     N  ", "                                 ",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "NbbbN NbbbN           NbbbN NbbbN", " CCC   CCC             CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    "NbbbN NbbbN    N N    NbbbN NbbbN", },
            { "                                 ", "         N   N     N   N         ", "         N   N     N   N         ",
                    "         N   N     N   N         ", "                                 ",
                    "                                 ", "                                 ",
                    "         N   N     N   N         ", "         N   N     N   N         ",
                    " NNN   NNN   N     N   NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN             NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN             NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    " NNN   NNN             NNN   NNN ", } };


    private static void centreModel(String[][] testShape) {

        int x = testShape.length / 2;
        int z = testShape[0][0].length() / 2;
        int y = testShape[0].length / 2;

        GL11.glTranslated(-x, -1-y, -1-z);

    }

    private void rotation() {
        float currentTimeInMs = System.currentTimeMillis();

        float angle = (currentTimeInMs / 10.0f) % 360;

        GL11.glRotatef((System.currentTimeMillis() / 16) % 360, 0.0f, 1, 0.0f);
    }

    private static final float scale = 0.999f;


/*
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
        if(tile.getWorldObj() == null) {
            return; // Always good to check if the world object is available.
        }

        RenderBlocks renderer = new RenderBlocks(tile.getWorldObj());

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        Block blockToRender = Blocks.diamond_block; // Assuming you want to render the block that this TileEntity represents.
        int metadata = 0;

        this.bindTexture(TextureMap.locationBlocksTexture);

        renderer.renderStandardBlock(Blocks.diamond_block, 0, 10, 0);
        GL11.glPopMatrix();
    }
*/

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
        if (!(tile instanceof TilePlanckScaleSpacetimeCompressionFabricator PSSCFRenderTile)) return;

        GL11.glPushMatrix();

        this.bindTexture(TextureMap.locationBlocksTexture);

        int xI = 0;
        int yI = 0;
        int zI = 0;

        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        GL11.glScalef(0.05f, 0.05f, 0.05f);

        rotation();

        centreModel(testShape);

        for (String[] layer : testShape) {
            for (String line : layer) {
                yI++;
                for (char block : line.toCharArray()) {
                    zI++;

                    if (block == ' ') continue;

                    GL11.glPushMatrix();

                    // Move block into place.
                    GL11.glTranslated(xI, yI, zI);

                    // Build it.
                    if (block == 'C') {
                        renderBlockInWorld(ItemList.Casing_Coil_Eternal.getBlock(), 13, scale);
                    } else if (block == 'N') {
                        renderBlockInWorld(GregTech_API.sBlockCasings1, 12, scale);
                    } else if (block == 'b') {
                        renderBlockInWorld(GregTech_API.sBlockCasings1, 13, scale);
                    } else {
                        renderBlockInWorld(GregTech_API.sBlockCasings1, 14, scale);
                    }

                    GL11.glPopMatrix();
                }
                zI = 0;
            }
            xI++;
            yI = 0;
        }

        GL11.glPopMatrix();
    }

}
