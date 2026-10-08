package view;

import model.User;

import java.nio.file.Path;

public interface View {

    void printMenu();
    MenuOption selectMenuOption();
    User inputUser();
    String inputDomain();
    Path inputPathToFile();
    void printSection(String text);
}
