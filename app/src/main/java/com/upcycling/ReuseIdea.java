package com.upcycling;

public class ReuseIdea {

    public enum ReuseType {
        PRACTICAL,
        ARTISTIC
    }

    static ReuseType getType(String reuseTypeString) {
        switch (reuseTypeString.toLowerCase()) {
            case "pratique":
                return ReuseType.PRACTICAL;
            case "artistique":
                return ReuseType.ARTISTIC;
            default:
                // TODO : Do error management
                return ReuseType.PRACTICAL;
        }
    }

    private ReuseType reuseType;
    private String name;
    private String description;
    private String urlInstructions;

    public ReuseIdea(ReuseType reuseType, String name, String description, String urlInstructions) {
        this.reuseType = reuseType;
        this.name = name;
        this.description = description;
        this.urlInstructions = urlInstructions;
    }

    public ReuseType getReuseType() {
        return reuseType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlInstructions() {
        return urlInstructions;
    }
}
