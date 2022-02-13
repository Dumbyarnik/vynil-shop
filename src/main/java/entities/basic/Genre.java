package entities.basic;

public enum Genre {
    ROCK,
    CLASSIC,
    ELECTRO,
    HIPHOP;

    public static boolean contains(String s) {
        for (Genre choice : values())
            if (choice.name().equals(s))
                return true;
        return false;
    }
}
