package view;

public enum MenuOption {
    ADD("Добавить данные вручную"),
    LOAD("Загрузить данные из файла"),
    Filter("Отфильтровать пользователей по домену почты"),
    EXIT("Выход");

    private final String text;

    MenuOption(String text) {
        this.text = text;
    }

    public String getStringMenuItem() {
        return String.format("%d. %s", this.ordinal() + 1, text);
    }

    public String getText() {
        return text;
    }
}
