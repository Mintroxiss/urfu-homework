package view;

import model.User;

import java.nio.file.Path;
import java.util.Scanner;

public class TerminalView implements View {

    private static final String DIVIDER = "-".repeat(20);

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void printMenu() {
        StringBuilder sb = new StringBuilder();
        MenuOption[] options = MenuOption.values();
        for (int i = 0; i < options.length; i++) {
            sb.append(options[i].getStringMenuItem());
            if (i + 1 < options.length) {
                sb.append("\n");
            }
        }
        printSection(sb.toString());
    }

    @Override
    public MenuOption selectMenuOption() {
        MenuOption[] menuOptions = MenuOption.values();
        while (true) {
            try {
                int command = Integer.parseInt(input());
                if (command > menuOptions.length || command <= 0) {
                    printSection("Несуществующий номер команды");
                    continue;
                }
                return menuOptions[command - 1];
            } catch (NumberFormatException e) {
                printSection("Несуществующая команда");
            }
        }
    }

    @Override
    public User inputUser() {
        try {
            printSection("Введите id");
            int id = inputNum();

            printSection("Введите ФИО");
            String fullName = input();

            printSection("Введите почту");
            String email = input();

            return new User(id, fullName, email);
        } catch (IllegalArgumentException e) {
            printSection(String.format("Невалидные данные: \"%s\"", e.getMessage()));
        }
        return null;
    }

    private int inputNum() {
        while (true) {
            try {
                return Integer.parseInt(input());
            } catch (NumberFormatException e) {
                printSection("Некорректное число: " + e.getMessage());
            }
        }
    }

    @Override
    public String inputDomain() {
        printSection("Введите домен почты, по которому будет совершена фильтрация");
        return input();
    }

    @Override
    public Path inputPathToFile() {
        printSection("Введите путь до файла");
        return Path.of(input());
    }

    @Override
    public void printSection(String text) {
        System.out.printf("%s%n%s%n", DIVIDER, text);
    }

    private String input() {
        System.out.println(DIVIDER);
        return scanner.nextLine();
    }
}
