package me.bryangaming.stafflab;

import me.bryangaming.stafflab.api.Core;
import me.bryangaming.stafflab.api.Loader;
import me.bryangaming.stafflab.api.Module;
import me.bryangaming.stafflab.data.ServerData;
import me.bryangaming.stafflab.loader.CommandLoader;
import me.bryangaming.stafflab.loader.EventLoader;
import me.bryangaming.stafflab.loader.file.FileLoader;
import me.bryangaming.stafflab.managers.SenderManager;

public class PluginCore implements Core{

    private final StaffLab staffLab;

    private FileLoader fileLoader;

    private ServerData serverData;
    private SenderManager senderManager;

    public PluginCore(StaffLab staffLab){
        this.staffLab = staffLab;
    }

    @Override
    public void init() {
        fileLoader = new FileLoader(staffLab);

        serverData = new ServerData();
        senderManager = new SenderManager(this);

        initLoaders(
                new CommandLoader(this),
                new EventLoader(this));
    }


    public void initLoaders(Loader... loaders){
        for (Loader loader : loaders){
            loader.load();
        }
    }

    public void initModules(Module... modules){
        for (Module module : modules){
            module.load();
        }
    }

    public ServerData getServerData(){
        return serverData;
    }

    public SenderManager getSenderManager(){
        return senderManager;
    }

    public FileLoader getFiles(){
        return fileLoader;
    }

    public StaffLab getPlugin(){
        return staffLab;
    }
}
