import model.Model;
import view.TerminalView;
import view.View;

public class Main {
    static void main() {
        View view = new TerminalView();
        Model model = new Model();
        Controller controller = new Controller(view, model);
        controller.run();
    }
}
