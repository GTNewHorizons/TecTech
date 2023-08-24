package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;

public class PSSCF_DTPF extends BasePSSCFStructure {

    public PSSCF_DTPF() {
        map.put('C', new BlockInfo(ItemList.Casing_Coil_Eternal.getBlock(), 13));
        map.put('N', new BlockInfo(GregTech_API.sBlockCasings1, 12));
        map.put('b', new BlockInfo(GregTech_API.sBlockCasings1, 13));
        map.put('s', new BlockInfo(GregTech_API.sBlockCasings1, 14));
        reverseInnerArrays(structure);
    }

    @Override
    public String[][] getStructureString() {
        return structure;
    }

    @SuppressWarnings("SpellCheckingInspection")
    private final String[][] structure = {
            { "                                 ", "         N   N     N   N         ",
                    "         N   N     N   N         ", "         N   N     N   N         ",
                    "                                 ", "                                 ",
                    "                                 ", "         N   N     N   N         ",
                    "         N   N     N   N         ", " NNN   NNN   N     N   NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN " },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ",
                    "         N   N     N   N         ", "                                 ",
                    "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "         bCCCb     bCCCb         ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN",
                    " CCC   CCC   N     N   CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", "NbbbN NbbbN           NbbbN NbbbN",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "                                 ", "  N     N               N     N  ",
                    "  N     N               N     N  ", "NbbbN NbbbN           NbbbN NbbbN",
                    " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", "NbbbN NbbbN    N N    NbbbN NbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "      NNNbbbbbNNsNNbbbbbNNN      ", "    ss   bCCCb     bCCCb   ss    ",
                    "   s     N   N     N   N     s   ", "   s                         s   ",
                    "  N      N   N     N   N      N  ", "  N      bCCCb     bCCCb      N  ",
                    "  N     sbbbbbNNsNNbbbbbs     N  ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN",
                    " CbC   CbC   N     N   CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", "NbbbN NbbbN           NbbbN NbbbN",
                    " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ",
                    "  s     s               s     s  ", " NNN   NNN             NNN   NNN ",
                    " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN",
                    " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", "NbbbN NbbbNNNNNsNsNNNNNbbbN NbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "    ss   bCCCb     bCCCb   ss    ", "         bCCCb     bCCCb         ",
                    "  s      NCCCN     NCCCN      s  ", "  s      NCCCN     NCCCN      s  ",
                    "         NCCCN     NCCCN         ", "         bCCCb     bCCCb         ",
                    "         bCCCb     bCCCb         ", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN",
                    " CCCCCCCCC   N     N   CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", "NbbbNNNbbbN           NbbbNNNbbbN",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "                                 ", "  N     N               N     N  ",
                    "  N     N               N     N  ", "NbbbNNNbbbN           NbbbNNNbbbN",
                    " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", "NbbbNNNbbbN    NbN    NbbbNNNbbbN", },
            { "                                 ", "         N   N     N   N         ",
                    "   s     N   N     N   N     s   ", "  s      NCCCN     NCCCN      s  ",
                    "                                 ", "                                 ",
                    "                                 ", "         NCCCN     NCCCN         ",
                    "         N   N     N   N         ", " NNN   NN    N     N    NN   NNN ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " NNN   NNN     NbN     NNN   NNN ", },
            { "                                 ", "                                 ",
                    "   s                         s   ", "  s      NCCCN     NCCCN      s  ",
                    "                                 ", "                                 ",
                    "                                 ", "         NCCCN     NCCCN         ",
                    "                                 ", "   N   N                 N   N   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   N   N                 N   N   ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "   N   N                 N   N   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   N   N       NbN       N   N   ", },
            { "                                 ", "         N   N     N   N         ",
                    "  N      N   N     N   N      N  ", "         NCCCN     NCCCN         ",
                    "                                 ", "                                 ",
                    "                                 ", "         NCCCN     NCCCN         ",
                    "         N   N     N   N         ", " NNN   NN    N     N    NN   NNN ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " NNN   NNN     NbN     NNN   NNN ", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "  N      bCCCb     bCCCb      N  ", "         bCCCb     bCCCb         ",
                    "         NCCCN     NCCCN         ", "         NCCCN     NCCCN         ",
                    "         NCCCN     NCCCN         ", "         bCCCb     bCCCb         ",
                    "         bCCCb     bCCCb         ", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN",
                    " CCCCCCCCC   N     N   CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", "NbbbNNNbbbN           NbbbNNNbbbN",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "                                 ", "  N     N               N     N  ",
                    "  N     N               N     N  ", "NbbbNNNbbbN           NbbbNNNbbbN",
                    " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", "NbbbNNNbbbN    NbN    NbbbNNNbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "  N     sbbbbbNNsNNbbbbbs     N  ", "         bCCCb     bCCCb         ",
                    "         N   N     N   N         ", "                                 ",
                    "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "  s     sbbbbbNNsNNbbbbbs     s  ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN",
                    " CbC   CbC   N     N   CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", "NbbbN NbbbN           NbbbN NbbbN",
                    " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ",
                    "  s     s               s     s  ", " NNN   NNN             NNN   NNN ",
                    " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN",
                    " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", "NbbbN NbbbNNNNNsNsNNNNNbbbN NbbbN", },
            { " NNN   NNN   N     N   NNN   NNN ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN",
                    "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN",
                    " NNN   NNN   N     N   NNN   NNN ", "   N   N                 N   N   ",
                    " NNN   NNN   N     N   NNN   NNN ", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN",
                    "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "NNNN   NNNCCCb     bCCCNNN   NNNN",
                    " CCC   CCC   N     N   CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", "NbbbN NbbbN           NbbbN NbbbN",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "                                 ", "  N     N               N     N  ",
                    "  N     N               N     N  ", "NbbbN NbbbN           NbbbN NbbbN",
                    " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", "NbbbN NbbbN    NbN    NbbbN NbbbN", },
            { "                                 ", " CCC   CCC   N     N   CCC   CCC ",
                    " CbC   CbC   N     N   CbC   CbC ", " CCCCCCCCC   N     N   CCCCCCCCC ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " CCCCCCCCC   N     N   CCCCCCCCC ",
                    " CbC   CbC   N     N   CbC   CbC ", " CCC   CCC   N     N   CCC   CCC ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN     NbN     NNN   NNN ", },
            { "                                 ", " CCC   CCC             CCC   CCC ",
                    " CbC   CbC             CbC   CbC ", " CCCCCCCCC             CCCCCCCCC ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " CCCCCCCCC             CCCCCCCCC ",
                    " CbC   CbC             CbC   CbC ", " CCC   CCC             CCC   CCC ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  N     N      NbN      N     N  ", },
            { "                                 ", " CCC   CCC             CCC   CCC ",
                    " CbC   CbC             CbC   CbC ", " CCCCCCCCC             CCCCCCCCC ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " CCCCCCCCC             CCCCCCCCC ",
                    " CbC   CbC             CbC   CbC ", " CCC   CCC             CCC   CCC ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  N     N      NbN      N     N  ", },
            { " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN",
                    "NbbbN NbbbN           NbbbN NbbbN", "NbbbNNNbbbN           NbbbNNNbbbN",
                    " NNN   NNN             NNN   NNN ", "   N   N                 N   N   ",
                    " NNN   NNN             NNN   NNN ", "NbbbNNNbbbN           NbbbNNNbbbN",
                    "NbbbN NbbbN           NbbbN NbbbN", "NbbbN NbbbN           NbbbN NbbbN",
                    " NNN   NNN             NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  N     N     NsNsN     N     N  ", },
            { "                                 ", "                                 ",
                    "  N     N               N     N  ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  N     N               N     N  ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  N     N    NbbbbbN    N     N  ", },
            { "                                 ", "                                 ",
                    "  N     N               N     N  ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  N     N               N     N  ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                N                ", " NsNNNNNsNNNNsbbbbbsNNNNsNNNNNsN ", },
            { "                                 ", "                                 ",
                    "  s     s               s     s  ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  s     s               s     s  ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                N                ",
                    "               NNN               ", "  NbbbbbNbbbbNbbbbbNbbbbNbbbbbN  ", },
            { "                                 ", "                                 ",
                    "  N     N               N     N  ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  N     N               N     N  ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                N                ", " NsNNNNNsNNNNsbbbbbsNNNNsNNNNNsN ", },
            { "                                 ", "                                 ",
                    "  N     N               N     N  ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "  N     N               N     N  ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  N     N    NbbbbbN    N     N  ", },
            { " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN",
                    "NbbbN NbbbN           NbbbN NbbbN", "NbbbNNNbbbN           NbbbNNNbbbN",
                    " NNN   NNN             NNN   NNN ", "   N   N                 N   N   ",
                    " NNN   NNN             NNN   NNN ", "NbbbNNNbbbN           NbbbNNNbbbN",
                    "NbbbN NbbbN           NbbbN NbbbN", "NbbbN NbbbN           NbbbN NbbbN",
                    " NNN   NNN             NNN   NNN ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  N     N     NsNsN     N     N  ", },
            { "                                 ", " CCC   CCC             CCC   CCC ",
                    " CbC   CbC             CbC   CbC ", " CCCCCCCCC             CCCCCCCCC ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " CCCCCCCCC             CCCCCCCCC ",
                    " CbC   CbC             CbC   CbC ", " CCC   CCC             CCC   CCC ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  N     N      NbN      N     N  ", },
            { "                                 ", " CCC   CCC             CCC   CCC ",
                    " CbC   CbC             CbC   CbC ", " CCCCCCCCC             CCCCCCCCC ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " CCCCCCCCC             CCCCCCCCC ",
                    " CbC   CbC             CbC   CbC ", " CCC   CCC             CCC   CCC ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "  N     N      NbN      N     N  ", },
            { "                                 ", " CCC   CCC   N     N   CCC   CCC ",
                    " CbC   CbC   N     N   CbC   CbC ", " CCCCCCCCC   N     N   CCCCCCCCC ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " CCCCCCCCC   N     N   CCCCCCCCC ",
                    " CbC   CbC   N     N   CbC   CbC ", " CCC   CCC   N     N   CCC   CCC ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN     NbN     NNN   NNN ", },
            { " NNN   NNN   N     N   NNN   NNN ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN",
                    "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN",
                    " NNN   NNN   N     N   NNN   NNN ", "   N   N                 N   N   ",
                    " NNN   NNN   N     N   NNN   NNN ", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN",
                    "NbbbN NbbNCCCb     bCCCNbbN NbbbN", "NNNN   NNNCCCb     bCCCNNN   NNNN",
                    " CCC   CCC   N     N   CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", "NbbbN NbbbN           NbbbN NbbbN",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "                                 ", "  N     N               N     N  ",
                    "  N     N               N     N  ", "NbbbN NbbbN           NbbbN NbbbN",
                    " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", "NbbbN NbbbN    NbN    NbbbN NbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "  N     sbbbbbNNsNNbbbbbs     N  ", "         bCCCb     bCCCb         ",
                    "         N   N     N   N         ", "                                 ",
                    "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "  s     sbbbbbNNsNNbbbbbs     s  ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN",
                    " CbC   CbC   N     N   CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", "NbbbN NbbbN           NbbbN NbbbN",
                    " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ",
                    "  s     s               s     s  ", " NNN   NNN             NNN   NNN ",
                    " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN",
                    " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", "NbbbN NbbbNNNNNsNsNNNNNbbbN NbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "  N      bCCCb     bCCCb      N  ", "         bCCCb     bCCCb         ",
                    "         NCCCN     NCCCN         ", "         NCCCN     NCCCN         ",
                    "         NCCCN     NCCCN         ", "         bCCCb     bCCCb         ",
                    "         bCCCb     bCCCb         ", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN",
                    " CCCCCCCCC   N     N   CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", "NbbbNNNbbbN           NbbbNNNbbbN",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "                                 ", "  N     N               N     N  ",
                    "  N     N               N     N  ", "NbbbNNNbbbN           NbbbNNNbbbN",
                    " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", "NbbbNNNbbbN    NbN    NbbbNNNbbbN", },
            { "                                 ", "         N   N     N   N         ",
                    "  N      N   N     N   N      N  ", "         NCCCN     NCCCN         ",
                    "                                 ", "                                 ",
                    "                                 ", "         NCCCN     NCCCN         ",
                    "         N   N     N   N         ", " NNN   NN    N     N    NN   NNN ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " NNN   NNN     NbN     NNN   NNN ", },
            { "                                 ", "                                 ",
                    "   s                         s   ", "  s      NCCCN     NCCCN      s  ",
                    "                                 ", "                                 ",
                    "                                 ", "         NCCCN     NCCCN         ",
                    "                                 ", "   N   N                 N   N   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   N   N                 N   N   ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", "   N   N                 N   N   ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", "   N   N       NbN       N   N   ", },
            { "                                 ", "         N   N     N   N         ",
                    "   s     N   N     N   N     s   ", "  s      NCCCN     NCCCN      s  ",
                    "                                 ", "                                 ",
                    "                                 ", "         NCCCN     NCCCN         ",
                    "         N   N     N   N         ", " NNN   NN    N     N    NN   NNN ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ",
                    "   C   C                 C   C   ", "   C   C                 C   C   ",
                    "   C   C                 C   C   ", " NNN   NNN     NbN     NNN   NNN ", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "    ss   bCCCb     bCCCb   ss    ", "         bCCCb     bCCCb         ",
                    "  s      NCCCN     NCCCN      s  ", "  s      NCCCN     NCCCN      s  ",
                    "         NCCCN     NCCCN         ", "         bCCCb     bCCCb         ",
                    "         bCCCb     bCCCb         ", "NbbbNNNbbNCCCb     bCCCNbbNNNbbbN",
                    " CCCCCCCCC   N     N   CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", "NbbbNNNbbbN           NbbbNNNbbbN",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "                                 ", "  N     N               N     N  ",
                    "  N     N               N     N  ", "NbbbNNNbbbN           NbbbNNNbbbN",
                    " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ",
                    " CCCCCCCCC             CCCCCCCCC ", "NbbbNNNbbbN    NbN    NbbbNNNbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "      NNNbbbbbNNsNNbbbbbNNN      ", "    ss   bCCCb     bCCCb   ss    ",
                    "   s     N   N     N   N     s   ", "   s                         s   ",
                    "  N      N   N     N   N      N  ", "  N      bCCCb     bCCCb      N  ",
                    "  N     sbbbbbNNsNNbbbbbs     N  ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN",
                    " CbC   CbC   N     N   CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", "NbbbN NbbbN           NbbbN NbbbN",
                    " NNN   NNN             NNN   NNN ", " NNN   NNN             NNN   NNN ",
                    "  s     s               s     s  ", " NNN   NNN             NNN   NNN ",
                    " NNN   NNN             NNN   NNN ", "NbbbN NbbbN           NbbbN NbbbN",
                    " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ",
                    " CbC   CbC             CbC   CbC ", "NbbbN NbbbNNNNNsNsNNNNNbbbN NbbbN", },
            { "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ",
                    "         N   N     N   N         ", "                                 ",
                    "         N   N     N   N         ", "         bCCCb     bCCCb         ",
                    "         bCCCb     bCCCb         ", "NbbbN NbbNCCCb     bCCCNbbN NbbbN",
                    " CCC   CCC   N     N   CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", "NbbbN NbbbN           NbbbN NbbbN",
                    "  N     N               N     N  ", "  N     N               N     N  ",
                    "                                 ", "  N     N               N     N  ",
                    "  N     N               N     N  ", "NbbbN NbbbN           NbbbN NbbbN",
                    " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ",
                    " CCC   CCC             CCC   CCC ", "NbbbN NbbbN    N N    NbbbN NbbbN", },
            { "                                 ", "         N   N     N   N         ",
                    "         N   N     N   N         ", "         N   N     N   N         ",
                    "                                 ", "                                 ",
                    "                                 ", "         N   N     N   N         ",
                    "         N   N     N   N         ", " NNN   NNN   N     N   NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ",
                    "                                 ", "                                 ",
                    "                                 ", " NNN   NNN             NNN   NNN ", } };

}
