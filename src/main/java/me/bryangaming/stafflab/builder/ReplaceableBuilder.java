package me.bryangaming.stafflab.builder;

public class ReplaceableBuilder {

    private final String target;
    private final String replacement;

    public ReplaceableBuilder(String target, String replacement){
        this.target = target;
        this.replacement = replacement;

    }
    public static ReplaceableBuilder create(String target, String replacement){
        return new ReplaceableBuilder(target, replacement);
    }

    public String getTarget(){
        return target;
    }

    public String getReplacement(){
        return replacement;
    }
}
