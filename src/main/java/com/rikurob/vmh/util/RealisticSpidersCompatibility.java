package com.rikurob.vmh.util;

public class RealisticSpidersCompatibility {

    private static final boolean REALISTIC_SPIDERS_LOADED = ModLoaderUtils.isModLoaded("realistic_spiders");

    public static final RealisticSpidersCompatibility INSTANCE = new RealisticSpidersCompatibility();

    public boolean rsEnabled;

    private RealisticSpidersCompatibility()
    {
        this.rsEnabled = REALISTIC_SPIDERS_LOADED;
    }
}
