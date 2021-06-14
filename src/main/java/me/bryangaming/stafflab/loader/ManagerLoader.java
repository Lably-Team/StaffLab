package me.bryangaming.stafflab.loader;

import me.bryangaming.stafflab.PluginCore;
import me.bryangaming.stafflab.managers.*;

public class ManagerLoader {

    private final PluginCore pluginCore;

    private SenderManager senderManager;
    private StaffModeManager staffModeManager;
    private FreezeManager freezeManager;
    private VanishManager vanishManager;
    private InvseeManager invseeManager;

    public ManagerLoader(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    public void load(){
        senderManager = new SenderManager(pluginCore);
        freezeManager = new FreezeManager(pluginCore);
        staffModeManager = new StaffModeManager(pluginCore);
        vanishManager = new VanishManager(pluginCore);
        invseeManager = new InvseeManager(pluginCore);
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

    public InvseeManager getInvseeManager() {
        return invseeManager;
    }
}
