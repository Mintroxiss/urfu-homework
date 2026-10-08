package model;

public record User(int id, String fullName, String email) {

    public User {
        if (id < 0) {
            throw new IllegalArgumentException();
        }
        fullName = fullName.strip();
        if (countCharacters(fullName, ' ') != 2) {
            throw new IllegalArgumentException(String.format("Некорректное ФИО: %s", fullName));
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException(String.format("Некорректная почта: %s", email));
        }
        if (countCharacters(email, '.') < 1 || !email.contains("@")) {
            throw new IllegalArgumentException(String.format("Некорректная почта: %s", fullName));
        }
    }

    private static int countCharacters(String str, char character) {
        return (int) str.chars().filter((ch) -> ch == character).count();
    }
}
