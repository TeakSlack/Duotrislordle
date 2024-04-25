package teak;

public class Main {
    public static void main(String[] args) {
        App app = App.getInstance();
        AppGUI.getInstance(); // hacky workaround to initialize gui

        try {
            app.run();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

