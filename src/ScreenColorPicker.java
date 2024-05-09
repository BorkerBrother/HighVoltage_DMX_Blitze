import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ScreenColorPicker {
    private Robot robot;
    private JFrame frame;
    private JLabel colorLabel;
    private JPanel colorDisplayPanel;  // Panel to display the picked color
    private JButton resetButton, storeButton;
    private JTextField xField, yField;
    private ArrayList<Point> coordinates;
    private Timer timer;
    private QLCPlusAPI qlcPlusAPI;
    int x;
    int y;
    String messagered;
    String messagegreen;
    String messageblue;

    public ScreenColorPicker() throws AWTException {
        robot = new Robot();
        frame = new JFrame("Screen Color Picker");
        colorLabel = new JLabel("RGB Value: ");
        colorDisplayPanel = new JPanel();  // Initialize the color display panel
        colorDisplayPanel.setPreferredSize(new Dimension(100, 100));  // Set size of the color panel
        resetButton = new JButton("Reset");
        storeButton = new JButton("Store Coordinates");
        xField = new JTextField(5);
        yField = new JTextField(5);
        coordinates = new ArrayList<>();
        qlcPlusAPI = new QLCPlusAPI();  // Create an instance of QLCPlusAPI

        frame.setLayout(new FlowLayout());

        frame.add(new JLabel("X:"));
        frame.add(xField);
        frame.add(new JLabel("Y:"));
        frame.add(yField);
        frame.add(storeButton);
        frame.add(resetButton);
        frame.add(colorLabel);
        frame.add(colorDisplayPanel);  // Add color panel to the frame
        frame.setSize(750, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        resetButton.addActionListener(e -> resetDisplay());
        storeButton.addActionListener(e -> storeCoordinates());

        timer = new Timer(100, e -> updateColorDisplay());
        timer.start();

    }

    private void updateColorDisplay() {
        Point point = MouseInfo.getPointerInfo().getLocation();
        Color color = robot.getPixelColor(x, y);

        colorDisplayPanel.setBackground(color);
        colorDisplayPanel.repaint();  // Repaint the panel to show the new color

        // Formatting the message for QLC+ which assumes "channel|value" format
        messagered = "0|" + color.getRed();  // Assuming channel 0 for red
        messagegreen = "1|" + color.getGreen();  // Assuming channel 1 for green
        messageblue = "2|" + color.getBlue();  // Assuming channel 2 for blue

        qlcPlusAPI.sendMessage(messagered);  // Send the red value via WebSocket
        qlcPlusAPI.sendMessage(messagegreen);  // Send the green value via WebSocket
        qlcPlusAPI.sendMessage(messageblue);  // Send the blue value via WebSocket



        colorLabel.setText("Current Position: " + point.toString() + " RGB: " + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue());
    }

    private void resetDisplay() {
        colorLabel.setText("RGB Value: ");
        colorDisplayPanel.setBackground(null);  // Reset the color display panel
        coordinates.clear();
    }

    private void storeCoordinates() {
        try {
            x = Integer.parseInt(xField.getText());
            y = Integer.parseInt(yField.getText());
            Color color = robot.getPixelColor(x, y);
            coordinates.add(new Point(x, y));

            // Formatting the message for QLC+ which assumes "channel|value" format
            messagered = "0|" + color.getRed();  // Assuming channel 0 for red
            messagegreen = "1|" + color.getGreen();  // Assuming channel 1 for green
            messageblue = "2|" + color.getBlue();  // Assuming channel 2 for blue

            qlcPlusAPI.sendMessage(messagered);  // Send the red value via WebSocket
            qlcPlusAPI.sendMessage(messagegreen);  // Send the green value via WebSocket
            qlcPlusAPI.sendMessage(messageblue);  // Send the blue value via WebSocket

            // Display all RGB values sent in one message dialog
            JOptionPane.showMessageDialog(frame, "Coordinates and RGB values sent:\nRed: " + messagered +
                                          "\nGreen: " + messagegreen + "\nBlue: " + messageblue);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid integers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        try {
            new ScreenColorPicker();
        } catch (AWTException e) {
            System.err.println("Error initializing Screen Color Picker: " + e.getMessage());
        }
    }
}
