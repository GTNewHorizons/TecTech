package com.github.technus.tectech.thing.block.PlanckScaleSpacetimeCompressionFabricator.Structures;

import gregtech.api.GregTech_API;

public class PSSCF_NanoForge extends BasePSSCFStructure {


    public PSSCF_NanoForge() {
        charToBlock.put('B', new BlockInfo(GregTech_API.sBlockCasings8, 10));
        charToBlock.put('C', new BlockInfo(GregTech_API.sBlockMachines, 4139));
        charToBlock.put('A', new BlockInfo(GregTech_API.sBlockCasings2, 5));
        reverseInnerArrays(structure);
        processStructureMap();
    }


    @Override
    public String[][] getStructureString() {
        return structure;
    }

    @SuppressWarnings("SpellCheckingInspection")
    private final String[][] structure = new String[][] {
            { "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "    CC                       ", "  CC                         ",
                    "                             ", "                             ", "                             ",
                    "                             ", "    CC                       ", "  CC                         ",
                    "                             ", "                             ", "                             ",
                    "                             ", "    CC                       ", "  CC                         ",
                    "                             ", "                             ", "                             ",
                    "                             ", "    CC                       ", "  CC                         ",
                    "                             ", " BBBBBB               BBBBBB " },
            { "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "      C                      ", "                             ", "                             ",
                    " C                           ", "                             ", "                             ",
                    "      C                      ", "                             ", "                             ",
                    " C                           ", "                             ", "                             ",
                    "      C                      ", "                             ", "                             ",
                    " C                           ", "                             ", "                             ",
                    "      C                      ", "                             ", "                             ",
                    " C                           ", "BBBBBBBB             BBBBBBBB" },
            { "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "   BB                        ",
                    " CCAA                        ", "C  BB                        ", "       C                     ",
                    "                             ", "                             ", "                             ",
                    "                             ", "C                            ", "   BB  C                     ",
                    "   BB                        ", "   BB                        ", "   BB                   BB   ",
                    "                        AA   ", "C                       BB   ", "       C                     ",
                    "                             ", "                             ", "                             ",
                    "                        BB   ", "C  BB                   AA   ", "   AA  C                BB   ",
                    "   BB                        ", "                             ", "                             ",
                    "                             ", "BBBBBBBB             BBBBBBBB" },
            { "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "  BBBB                       ",
                    "  ABBA                       ", "C BBBB                       ", "   BB  C                     ",
                    "   BB                        ", "   BB                        ", "   BB                        ",
                    "   BB         B              ", "C  BB         B              ", "  BBBB C      B              ",
                    "  BBBB        B              ", "  BBBB        B              ", "  BBBB        B        BBBB  ",
                    "   BB         B        ABBA  ", "C  BB         B        BBBB  ", "   BB  C                BB   ",
                    "   BB                   BB   ", "   BB                   BB   ", "   BB                   BB   ",
                    "   BB                  BBBB  ", "C BBBB                 ABBA  ", "  ABBA C               BBBB  ",
                    "  BBBB                  BB   ", "   BB                   BB   ", "   BB                   BB   ",
                    "   BB                   BB   ", "BBBBBBBB             BBBBBBBB" },
            { "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "  BBBB                       ",
                    "  ABBA                       ", "  BBBB C                     ", "C  BB        CBC             ",
                    "   BB        CBC             ", "   BB        CBC             ", "   BB        CBC             ",
                    "   BB        CBC             ", "   BB  C     CBC             ", "C BBBB       CBC             ",
                    "  BBBB       CBC             ", "  BBBB       CBC             ", "  BBBB       CBC       BBBB  ",
                    "   BB        CBC       ABBA  ", "   BB  C     CBC       BBBB  ", "C  BB        CBC        BB   ",
                    "   BB        CBC        BB   ", "   BB        CBC        BB   ", "   BB        CBC        BB   ",
                    "   BB                  BBBB  ", "  BBBB C               ABBA  ", "C ABBA                 BBBB  ",
                    "  BBBB                  BB   ", "   BB                   BB   ", "   BB                   BB   ",
                    "   BB                   BB   ", "BBBBBBBB    BBBBB    BBBBBBBB" },
            { "              C              ", "              C              ", "              C              ",
                    "              C              ", "              C              ", "             CBC             ",
                    "             CBC             ", "             CBC             ", "             CBC             ",
                    "             CBC             ", "             CBC             ", "   BB        CBC             ",
                    "   AACC      CBC             ", "   BB  C     CBC             ", "C           CB BC            ",
                    "            CB BC            ", "            CB BC            ", "            CB BC            ",
                    "            BB BB            ", "       C    BB BB            ", "C  BB       BB BB            ",
                    "   BB       BB BB            ", "   BB       BB BB            ", "   BB       BB BB       BB   ",
                    "            BB BB       AA   ", "       C    BB BB       BB   ", "C           CB BC            ",
                    "            CB BC            ", "            CB BC            ", "            CB BC            ",
                    "             CBC        BB   ", "   BB  C     CBC        AA   ", "C  AA        CBC        BB   ",
                    "   BB        CBC             ", "             CBC             ", "             CBC             ",
                    "             CBC             ", "BBBBBBBB   BBBBBBB   BBBBBBBB" },
            { "              B              ", "              B              ", "              B              ",
                    "              B              ", "              B              ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    " C           B B             ", "            B   B            ", "            B   B            ",
                    "      C     B   B            ", "            B   B            ", "            B   B            ",
                    " C         BB   BB           ", "           BB   BB           ", "            B   B            ",
                    "      C     B   B            ", "            B   B            ", "            B   B            ",
                    " C          B   B            ", "             B B             ", "             B B             ",
                    "      C      B B             ", "             B B             ", "             B B             ",
                    " C           B B             ", "             B B             ", "             B B             ",
                    "      C      B B             ", "BBBBBBBB  BBBBBBBBB  BBBBBBBB" },
            { "              B              ", "              B              ", "              B              ",
                    "              B              ", "              B              ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "  CC        B   B            ", "    CC      B   B            ",
                    "            B   B            ", "            B   B            ", "            B   B            ",
                    "           BB   BB           ", "  CC       BB   BB           ", "    CC      B   B            ",
                    "            B   B            ", "            B   B            ", "            B   B            ",
                    "            B   B            ", "  CC         B B             ", "    CC       B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "  CC         B B             ", "    CC       B B             ",
                    "             B B             ", " BBBBBB   BBBBBBBBB   BBBBBB " },
            { "              B              ", "              B              ", "              B              ",
                    "              B              ", "              B              ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "            B   B            ", "            B   B            ",
                    "            B   B            ", "            B   B            ", "            B   B            ",
                    "           BB   BB           ", "           BB   BB           ", "            B   B            ",
                    "            B   B            ", "            B   B            ", "            B   B            ",
                    "            B   B            ", "             B B             ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "          BBBBBBBBB          " },
            { "              B              ", "              B              ", "              B              ",
                    "              B              ", "              B              ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "            B   B            ", "            B   B            ",
                    "            B   B            ", "            B   B            ", "            B   B            ",
                    "           BB   BB           ", "           BB   BB           ", "            B   B            ",
                    "            B   B            ", "            B   B            ", "            B   B            ",
                    "            B   B            ", "             B B             ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "             B B             ", "             B B             ",
                    "             B B             ", "          BBBBBBBBB          " },
            { "              C              ", "              C              ", "              C              ",
                    "              C              ", "              C              ", "             CBC             ",
                    "             CBC             ", "             CBC             ", "             CBC             ",
                    "             CBC             ", "             CBC             ", "             CBC             ",
                    "             CBC             ", "             CBC             ", "            CB BC            ",
                    "            CB BC            ", "            CB BC            ", "            CB BC            ",
                    "            BB BB            ", "            BB BB            ", "            BB BB            ",
                    "            BB BB            ", "            BB BB            ", "            BB BB            ",
                    "            BB BB            ", "            BB BB            ", "            CB BC            ",
                    "            CB BC            ", "            CB BC            ", "            CB BC            ",
                    "             CBC             ", "             CBC             ", "             CBC             ",
                    "             CBC             ", "             CBC             ", "             CBC             ",
                    "             CBC             ", "           BBBBBBB           " },
            { "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "             CBC             ",
                    "             CBC             ", "             CBC             ", "             CBC             ",
                    "             CBC             ", "             CBC             ", "             CBC             ",
                    "             CBC             ", "             CBC             ", "             CBC             ",
                    "             CBC             ", "             CBC             ", "             CBC             ",
                    "             CBC             ", "             CBC             ", "             CBC             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "                             ", "                             ",
                    "                             ", "            BBBBB            " } };

}
