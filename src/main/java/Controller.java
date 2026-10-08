import model.Model;
import view.MenuOption;
import view.View;

import java.util.Locale;

public class Controller {

    private final Model model;
    private final View view;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    public void run() {
        boolean isEnd = false;
        while (!isEnd) {
            view.printMenu();
            MenuOption option = view.selectMenuOption();
            view.printSection(option.getText().toUpperCase(Locale.ROOT));
            switch (option) {
                case ADD -> view.printSection(model.addUser(view.inputUser()) ?
                        "Пользователь добавлен в список" :
                        "Пользователь не добавлен в список");
                case LOAD -> {
                    try {
                        view.printSection(model.addUsersFromFile(view.inputPathToFile()));
                    } catch (Exception e) {
                        view.printSection(String.format("Ошибка: \"%s\"", e.getMessage()));
                    }
                }
                case Filter -> view.printSection(model.findUsersByDomain(view.inputDomain()));
                case EXIT -> isEnd = true;
            }
        }
    }
}
