package me.bryangaming.stafflab.loader.file;

import me.bryangaming.stafflab.StaffLab;
import me.bryangaming.stafflab.api.Loader;

public class FileLoader implements Loader {

    private StaffLab staffLab;

    private FileManager configFile;
    private FileManager messageFile;

    public FileLoader(StaffLab staffLab){
        this.staffLab = staffLab;
        load();
    }

    @Override
    public void load() {
        configFile = new FileManager(staffLab, "config.yml");
        messageFile = new FileManager(staffLab, "messages.yml");
    }

    public FileManager getConfigFile(){
        return configFile;
    }

    public FileManager getMessagesFile(){
        return messageFile;
    }
}
