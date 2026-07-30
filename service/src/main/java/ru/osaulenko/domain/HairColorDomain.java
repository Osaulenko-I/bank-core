package ru.osaulenko.domain;

public enum HairColorDomain {
    BLOND("blond"),
    DARK("dark"),
    BRUNETTE("brunette"),
    RED("red");

    private final String type;

    private HairColorDomain(String type) {
        this.type = type;
    }

    public static HairColorDomain fromString(String type) {
        for (HairColorDomain c : values()) {
            if (c.type.equals(type))
                return c;
        }

        throw new IllegalArgumentException("Hair not found");
    }
}
