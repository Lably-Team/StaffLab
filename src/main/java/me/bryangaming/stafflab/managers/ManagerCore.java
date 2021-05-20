package me.bryangaming.stafflab.managers;

import me.bryangaming.stafflab.PluginCore;

public class ManagerCore {

    private PluginCore pluginCore;

    private SenderManager senderManager;
    private StaffModeManager staffModeManager;
    private FreezeManager freezeManager;

    public ManagerCore(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    public void load(){
        senderManager = new SenderManager(pluginCore);
        freezeManager = new FreezeManager(pluginCore);
        staffModeManager = new StaffModeManager(pluginCore);
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
}
