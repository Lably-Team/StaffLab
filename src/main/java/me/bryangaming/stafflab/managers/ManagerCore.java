package me.bryangaming.stafflab.managers;

import me.bryangaming.stafflab.PluginCore;

public class ManagerCore {

    private final PluginCore pluginCore;

    private SenderManager senderManager;
    private StaffModeManager staffModeManager;
    private FreezeManager freezeManager;
    private VanishManager vanishManager;

    public ManagerCore(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    public void load(){
        senderManager = new SenderManager(pluginCore);
        freezeManager = new FreezeManager(pluginCore);
        staffModeManager = new StaffModeManager(pluginCore);
        vanishManager = new VanishManager(pluginCore);
    }

    public SenderManager getSenderManager() {
        return senderManager;
    }

    public StaffModeManager getStaffModeManager() {
        return staffModeManager;
    }

    public FreezeManager getFreezeManager() {
        return freezeManager;
    }

    public VanishManager getVanishManager(){
        return vanishManager;
    }
}
