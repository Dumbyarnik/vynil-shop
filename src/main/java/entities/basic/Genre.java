/*
    @author: Dennis Dreier
*/
package entities.basic;

public enum Genre {
    ROCK,
    CLASSIC,
    ELECTRO,
    HIPHOP;

    public static boolean contains(String s) {
        for (Genre choice : values())
            if (choice.name().equals(s.toUpperCase()))
                return true;
        return false;
    }
}
