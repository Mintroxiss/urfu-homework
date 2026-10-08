package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Model {

    private final List<User> users = new ArrayList<>();

    private int longestId = 0;
    private int longestFullName = 0;
    private int longestEmail = 0;

    /// Добавляет пользователя в коллекцию
    ///
    /// @param user пользователь
    /// @return {@code true} - пользователь добавлен;
    ///         {@code false} - пользователь не добавлен, поскольку его id уже есть в коллекции
    ///         или он равен {@code null}
    public boolean addUser(User user) {
        if (user == null || users.stream().anyMatch((u) -> u.id() == user.id())) {
            return false;
        }
        users.add(user);

        countLongestValues(user);

        return true;
    }

    private void countLongestValues(User user) {
        int idLength = Integer.toString(user.id()).length();
        if (idLength > longestId) {
            longestId = idLength;
        }

        int fullNameLength = user.fullName().length();
        if (fullNameLength > longestFullName) {
            longestFullName = fullNameLength;
        }

        int emailLength = user.email().length();
        if (emailLength > longestEmail) {
            longestEmail = emailLength;
        }
    }

    public String addUsersFromFile(Path path) {
        List<User> loaded = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(",", 3);
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Неверный формат строки: " + line);
                }
                loaded.add(new User(
                        Integer.parseInt(parts[0].trim()),
                        parts[1].trim(),
                        parts[2].trim()));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Не удалось прочитать файл " + path, e);
        }

        List<User> added = new ArrayList<>();
        for (User user : loaded) {
            if (addUser(user)) {
                added.add(user);
            }
        }

        return added.stream()
                .map(User::toString)
                .collect(Collectors.joining("\n"));
    }

    public String findUsersByDomain(String domain) {
        if (users.isEmpty()) {
            return "Нет пользователей в коллекции";
        }
        String template = "%-" + longestId + "d | %-" + longestFullName + "s | %-" + longestEmail + "s";
        String res = users.stream()
                .filter((u) -> u.email().contains("@" + domain))
                .sorted(Comparator.comparingInt(User::id))
                .map((u) -> String.format(template, u.id(), u.fullName(), u.email()))
                .collect(Collectors.joining("\n"));
        if (res.isEmpty()) {
            return "Нету пользователей с введённым доменом почты";
        }
        return res;
    }
}
