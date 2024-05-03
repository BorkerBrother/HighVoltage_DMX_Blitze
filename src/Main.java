import java.awt.AWTException;
// test 3
public class Main {
    public static void main(String[] args) {
        try {
            new ScreenColorPicker();
        } catch (AWTException e) {
            System.err.println("Error initializing Screen Color Picker: " + e.getMessage());
        }
    }
}
