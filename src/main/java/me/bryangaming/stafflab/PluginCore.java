package me.bryangaming.stafflab;

import me.bryangaming.stafflab.api.Core;
import me.bryangaming.stafflab.api.Loader;
import me.bryangaming.stafflab.data.ServerData;
import me.bryangaming.stafflab.loader.CommandLoader;
import me.bryangaming.stafflab.loader.DataLoader;
import me.bryangaming.stafflab.loader.EventLoader;
import me.bryangaming.stafflab.loader.file.FileLoader;
import me.bryangaming.stafflab.loader.ManagerLoader;

public class PluginCore implements Core{

    private final StaffLab staffLab;

    private FileLoader fileLoader;

    private ServerData serverData;
    private ManagerLoader managerLoader;

    public PluginCore(StaffLab staffLab){
        this.staffLab = staffLab;
    }

    @Override
    public void init() {
        fileLoader = new FileLoader(staffLab);

        serverData = new ServerData();

        managerLoader = new ManagerLoader(this);
        managerLoader.load();

        initLoaders(
                new CommandLoader(this),
                new EventLoader(this),
                new DataLoader(this));
    }


    public void initLoaders(Loader... loaders){
        for (Loader loader : loaders){
            loader.load();
        }
    }

    public ServerData getServerData(){
        return serverData;
    }

    public ManagerLoader getManagers(){
        return managerLoader;
    }

    public FileLoader getFiles(){
        return fileLoader;
    }

    public StaffLab getPlugin(){
        return staffLab;
    }
}
