import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

abstract class SmartDevice implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private boolean state;

    public SmartDevice(String name) {
        this.name = name;
        state = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}

class LightBulb extends SmartDevice {
    private int brigtness;
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBrigtness() {
        return brigtness;
    }

    public void setBrigtness(int brigtness) {
        if (brigtness < 0 || brigtness > 100) {
            brigtness = 0;
        } else {
            this.brigtness = brigtness;
        }
    }

    public LightBulb(String name, String color) {
        super(name);
        this.color = color;
        brigtness = 0;
    }
}

class Fan extends SmartDevice {

    private int speed;

    public Fan(String name, int speed) {
        super(name);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}

class thermostats extends SmartDevice {
    private double temperature;

    public thermostats(String name, double temperature) {
        super(name);
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

}

class cameras extends SmartDevice {
    private String resolution;
    private String format;

    public cameras(String name, String resolution, String format) {
        super(name);
        this.resolution = resolution;
        this.format = format;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}

public class Project {
    public static void main(String[] args) {
        new App();
    }
}

class App {

    final int APP_WIDTH = 1280;
    final int APP_HEIGHT = 720;

    JFrame outerFrame;
    JPanel hiddenWindow;
    Image backgroundImage;
    JButton[] buttons;
    String[] devices = { "LightBulb", "Fan", "Thermostat", "Camera" };
    ArrayList<SmartDevice> addedDevices;

    private String[] buttonLabels = { "Add Device", "Configure Device", "Remove Device", "Credits", "Exit" };
    private int buttonWidth = (int) (APP_WIDTH * 0.20);
    private int buttonHeight = 50;
    private int startX;
    private int startY;

    App() {
        backgroundImage = new ImageIcon("background.jpg").getImage();
        outerFrame = new JFrame();
        buttons = new JButton[5];
        initializeGUI();

        addedDevices = new ArrayList<>();
        loadFromFile();
    }

    private void initializeGUI() {
        outerFrame.setTitle("Project For Mohtarma");
        outerFrame.setSize(APP_WIDTH, APP_HEIGHT);
        outerFrame.setLocationRelativeTo(null);
        outerFrame.setResizable(false);
        outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startX = (int) (APP_WIDTH * 0.3) / 2 - buttonWidth / 2;
        startY = (APP_HEIGHT - (buttonLabels.length * buttonHeight + (buttonLabels.length - 1) * 10)) / 2;

        JPanel mainPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(backgroundImage, 0, 0, APP_WIDTH, APP_HEIGHT, null);

                Graphics2D g2d = (Graphics2D) g;
                Color c = new Color(0, 0, 0, 150);
                g2d.setColor(c);
                g2d.fillRect(0, 0, (int) (APP_WIDTH * 0.3), APP_HEIGHT);
            }
        };

        hiddenWindow = new JPanel();
        hiddenWindow.setBounds((int) (APP_WIDTH * 0.3), 0, (int) (APP_WIDTH * 0.7), APP_HEIGHT);
        hiddenWindow.setOpaque(false);

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setBounds(startX, startY + i * (buttonHeight + 10), buttonWidth, buttonHeight);
            buttons[i].addActionListener(new ButtonListener());
            mainPanel.add(buttons[i]);
        }

        mainPanel.add(hiddenWindow);
        mainPanel.setLayout(null);

        outerFrame.add(mainPanel);
        outerFrame.setVisible(true);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Add Device")) {

                JLabel msg = new JLabel("Select a Device to Add:");
                JList<String> deviceList = new JList<>(devices);
                deviceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                deviceList.setBounds(0, 0, 250, 120);

                JButton addButton = new JButton("Add");

                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        String selectedDevice = deviceList.getSelectedValue();
                        if (selectedDevice != null) {

                            String deviceName = JOptionPane.showInputDialog(hiddenWindow,
                                    "Enter a name for the " + selectedDevice + ":",
                                    "Device Name Input", JOptionPane.PLAIN_MESSAGE);

                            if (deviceName != null) {
                                SmartDevice newDevice = null;
                                if (selectedDevice.equals("LightBulb")) {
                                    newDevice = new LightBulb(deviceName, "White");
                                } else if (selectedDevice.equals("Fan")) {
                                    newDevice = new Fan(deviceName, 0);
                                } else if (selectedDevice.equals("Thermostat")) {
                                    newDevice = new thermostats(deviceName, 22.0);
                                } else if (selectedDevice.equals("Camera")) {
                                    newDevice = new cameras(deviceName, "1080p", "MP4");
                                }

                                if (newDevice != null) {
                                    addedDevices.add(newDevice);
                                    JOptionPane.showMessageDialog(hiddenWindow,
                                            selectedDevice + " named \"" + deviceName + "\" has been added!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(hiddenWindow,
                                        "Device name cannot be empty. Please try again.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(hiddenWindow, "No device selected. Please select a device.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                JPanel addDevicePanel = new JPanel();
                addDevicePanel.setLayout(new BorderLayout());
                addDevicePanel.add(msg, BorderLayout.NORTH);
                addDevicePanel.add(deviceList, BorderLayout.CENTER);
                addDevicePanel.add(addButton, BorderLayout.SOUTH);

                JOptionPane.showMessageDialog(hiddenWindow, addDevicePanel, "Add Device", JOptionPane.PLAIN_MESSAGE);

            } else if (e.getActionCommand().equals("Configure Device")) {

                if (addedDevices.isEmpty()) {
                    JOptionPane.showMessageDialog(hiddenWindow, "No devices to configure.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] deviceNames = new String[addedDevices.size()];
                for (int i = 0; i < addedDevices.size(); i++) {
                    deviceNames[i] = addedDevices.get(i).getName();
                }

                String selectedDeviceName = (String) JOptionPane.showInputDialog(hiddenWindow,
                        "Select a Device to Configure:", "Configure Device",
                        JOptionPane.PLAIN_MESSAGE, null, deviceNames, null);

                if (selectedDeviceName == null) {
                    return;
                }

                SmartDevice selectedDevice = null;
                for (SmartDevice device : addedDevices) {
                    if (device.getName().equals(selectedDeviceName)) {
                        selectedDevice = device;
                        break;
                    }
                }

                if (selectedDevice == null) {
                    JOptionPane.showMessageDialog(hiddenWindow, "Device not found.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JPanel configPanel = new JPanel();
                configPanel.setLayout(new BorderLayout());

                JLabel configLabel = new JLabel("Configure " + selectedDevice.getName() + ":");
                configPanel.add(configLabel, BorderLayout.NORTH);

                if (selectedDevice instanceof LightBulb) {
                    LightBulb lightBulb = (LightBulb) selectedDevice;

                    JPanel lightBulbPanel = new JPanel();
                    lightBulbPanel.setLayout(new GridLayout(2, 2, 10, 10));

                    JLabel brightnessLabel = new JLabel("Brightness:");
                    JSlider brightnessSlider = new JSlider(0, 100, lightBulb.getBrigtness());
                    brightnessSlider.setMajorTickSpacing(25);
                    brightnessSlider.setPaintTicks(true);
                    brightnessSlider.setPaintLabels(true);

                    JLabel colorLabel = new JLabel("Color:");
                    JTextField colorField = new JTextField(lightBulb.getColor());

                    lightBulbPanel.add(brightnessLabel);
                    lightBulbPanel.add(brightnessSlider);
                    lightBulbPanel.add(colorLabel);
                    lightBulbPanel.add(colorField);

                    configPanel.add(lightBulbPanel, BorderLayout.CENTER);

                    int result = JOptionPane.showConfirmDialog(hiddenWindow, configPanel, "LightBulb Configuration",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        lightBulb.setBrigtness(brightnessSlider.getValue());
                        lightBulb.setColor(colorField.getText());
                        JOptionPane.showMessageDialog(hiddenWindow, "LightBulb updated successfully!");
                    }

                } else if (selectedDevice instanceof Fan) {
                    Fan fan = (Fan) selectedDevice;

                    JLabel speedLabel = new JLabel("Speed:");
                    JSlider speedSlider = new JSlider(0, 5, fan.getSpeed());
                    speedSlider.setMajorTickSpacing(1);
                    speedSlider.setPaintTicks(true);
                    speedSlider.setPaintLabels(true);

                    configPanel.add(speedLabel, BorderLayout.NORTH);
                    configPanel.add(speedSlider, BorderLayout.CENTER);

                    int result = JOptionPane.showConfirmDialog(hiddenWindow, configPanel, "Fan Configuration",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        fan.setSpeed(speedSlider.getValue());
                        JOptionPane.showMessageDialog(hiddenWindow, "Fan updated successfully!");
                    }

                } else if (selectedDevice instanceof thermostats) {
                    thermostats thermostat = (thermostats) selectedDevice;

                    JLabel tempLabel = new JLabel("Temperature (°C):");
                    JSlider tempSlider = new JSlider(10, 30, (int) thermostat.getTemperature());
                    tempSlider.setMajorTickSpacing(5);
                    tempSlider.setPaintTicks(true);
                    tempSlider.setPaintLabels(true);

                    configPanel.add(tempLabel, BorderLayout.NORTH);
                    configPanel.add(tempSlider, BorderLayout.CENTER);

                    int result = JOptionPane.showConfirmDialog(hiddenWindow, configPanel,
                            "Thermostat Configuration",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        thermostat.setTemperature(tempSlider.getValue());
                        JOptionPane.showMessageDialog(hiddenWindow, "Thermostat updated successfully!");
                    }

                } else if (selectedDevice instanceof cameras) {
                    cameras camera = (cameras) selectedDevice;

                    JPanel cameraPanel = new JPanel();
                    cameraPanel.setLayout(new GridLayout(2, 2, 10, 10));

                    JLabel resolutionLabel = new JLabel("Resolution:");
                    JTextField resolutionField = new JTextField(camera.getResolution());

                    JLabel formatLabel = new JLabel("Format:");
                    JTextField formatField = new JTextField(camera.getFormat());

                    cameraPanel.add(resolutionLabel);
                    cameraPanel.add(resolutionField);
                    cameraPanel.add(formatLabel);
                    cameraPanel.add(formatField);

                    configPanel.add(cameraPanel, BorderLayout.CENTER);

                    int result = JOptionPane.showConfirmDialog(hiddenWindow, configPanel, "Camera Configuration",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        camera.setResolution(resolutionField.getText());
                        camera.setFormat(formatField.getText());
                        JOptionPane.showMessageDialog(hiddenWindow, "Camera updated successfully!");
                    }
                }
            } else if (e.getActionCommand().equals("Remove Device")) {
                if (addedDevices.isEmpty()) {
                    JOptionPane.showMessageDialog(hiddenWindow, "No devices to remove.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] deviceNames = new String[addedDevices.size()];
                for (int i = 0; i < addedDevices.size(); i++) {
                    deviceNames[i] = addedDevices.get(i).getName();
                }

                JLabel msg = new JLabel("Select a Device to Remove:");
                JList<String> deviceList = new JList<>(deviceNames);
                deviceList.setBounds(0, 0, 250, 120);

                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        int selectedIndex = deviceList.getSelectedIndex();
                        if (selectedIndex != -1) {
                            SmartDevice removedDevice = addedDevices.remove(selectedIndex);
                            JOptionPane.showMessageDialog(hiddenWindow,
                                    removedDevice.getName() + " has been removed successfully!");
                        } else {
                            JOptionPane.showMessageDialog(hiddenWindow,
                                    "No device selected. Please select a device to remove.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                JPanel removeDevicePanel = new JPanel();
                removeDevicePanel.setLayout(new BorderLayout());
                removeDevicePanel.add(msg, BorderLayout.NORTH);
                removeDevicePanel.add(new JScrollPane(deviceList), BorderLayout.CENTER);
                removeDevicePanel.add(removeButton, BorderLayout.SOUTH);

                JOptionPane.showMessageDialog(hiddenWindow, removeDevicePanel, "Remove Device",
                        JOptionPane.PLAIN_MESSAGE);

            } else if (e.getActionCommand().equals("Credits")) {
                String credits = "<html>" +
                        "<div style='text-align: center; font-family: Georgia, serif;'>" +
                        "<h2 style='color:#C8733A;'>Acknowledgments</h2>" +
                        "<p style='font-size: 14px; color: #333;'>This work reflects the dedication of:</p>" +
                        "<br>" +
                        "<p style='font-size: 16px; color:#ce893f;'>Areeba Irfan</p>" +
                        "<p style='font-size: 16px; color:#ce893f;'>Ukasha Anwar</p>" +
                        "<p style='font-size: 16px; color:#ce893f;'>Muhammad Nouman</p>" +
                        "<br>" +
                        "<p style='font-size: 14px; color: #555;'>Heartfelt thanks to:</p>" +
                        "<br>" +
                        "<p style='font-size: 14px; color: #555;'>Professors and Lab Instructors for their guidance</p>"
                        +
                        "<br>" +
                        "<p style='font-size: 12px; color: #888888;'>\"Every piece of code is a story in the making. Write it with care, craft it with purpose.\"</p>"
                        +
                        "</div>" +
                        "</html>";

                JOptionPane.showMessageDialog(hiddenWindow, credits, "Credits", JOptionPane.PLAIN_MESSAGE);

            } else if (e.getActionCommand().equals("Exit")) {
                saveToFile();
                System.exit(0);
            }
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream("Devices.ser"))) {
            write.writeObject(addedDevices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream("Devices.ser"))) {
            addedDevices = (ArrayList<SmartDevice>) read.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}