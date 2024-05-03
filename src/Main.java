import java.awt.AWTException;

public class Main {
    public static void main(String[] args) {
        try {
            new ScreenColorPicker();
        } catch (AWTException e) {
            System.err.println("Error initializing Screen Color Picker: " + e.getMessage());
        }
    }
}
