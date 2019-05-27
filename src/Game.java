import view.MainWindow;


public class Game {
    public static void main(String[] args) {
        try {
            MainWindow frame = new MainWindow();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
