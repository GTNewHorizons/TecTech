package com.github.technus.tectech.mechanics.dataTransport;

import java.util.HashMap;
import java.util.Map;

public class WirelessDataNetwork {

    private static final Map<String, WirelessInventoryDataPacket> networkData = new HashMap<>();

    public static void storeData(String networkId, WirelessInventoryDataPacket dataPacket) {
        networkData.put(networkId, dataPacket);
    }

    public static WirelessInventoryDataPacket retrieveData(String networkId) {
        return networkData.get(networkId);
    }

    public static void clearData(String networkId) {
        networkData.remove(networkId);
    }
}
