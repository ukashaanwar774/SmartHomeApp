
Smart Device Management Application

Overview:
This application is a GUI-based Smart Device Management System that allows users to add, configure,
and remove various smart devices such as Light Bulbs, Fans, Thermostats, and Cameras.
The application is designed using Java Swing for the graphical user interface and employs
object-oriented programming principles for modularity and extensibility.

Class Structures:

1. Abstract Class - SmartDevice:
   - Purpose: Serves as the base class for all devices. It holds common properties and methods.
   - Attributes:
     • String name
     • Boolean state
   - Methods:
     • getName()
     • setName()
     • isState()
     • setState()
   - Description: Serves as a blueprint for all device types.

2. Concrete Classes:
   - Lightbulb:
     • Purpose: Represents a light bulb smart device.
     • Attributes: int brightness, String color
     • Methods: getBrightness(), setBrightness(), getColor(), setColor()
     • Constructor: Accepts name and default color.

   - Fan:
     • Purpose: Represents a fan smart device.
     • Attribute: int speed
     • Methods: getSpeed(), setSpeed()
     • Constructor: Accepts name and default speed.

   - Thermostat:
     • Purpose: Represents a thermostat smart device.
     • Attribute: double temperature
     • Methods: getTemperature(), setTemperature()
     • Constructor: Accepts name and default temperature.

   - Camera:
     • Purpose: Represents a camera smart device.
     • Attributes: String resolution, String format
     • Methods: getResolution(), setResolution(), getFormat(), setFormat()
     • Constructor: Accepts name, resolution, and format.

3. Main Class - Project:
   - Purpose: Entry point for the application. Initializes the App class.

4. App Class:
   - Purpose: Represents the GUI for the application.
   - Fields:
     • APP_WIDTH, APP_HEIGHT
     • outerFrame (main window)
     • hiddenWindow (panel for device interaction)
     • buttons (array of buttons: Add, Configure, Remove, etc.)
     • devices (list of device names)
     • addedDevices (list of smart devices added)
   - Constructor:
     • Initializes the frame and sets up layout.
     • Sets background image.
     • Creates JFrame window and layout with JPanel.
     • Adds buttons with action listeners.

5. Button Listener Class:
   - Purpose: Handles button click actions.
   - Add Device:
     • Dialog to choose device type and enter name.
     • Creates device and adds to addedDevices list.
   - Configure Device:
     • Lists added devices and shows configuration options based on type.
     • Updates device properties.
   - Remove Device:
     • Lists added devices and removes selected one.
   - Credits:
     • Displays a credits window with acknowledgments.

6. Event Handling:
   - Each button has an action handler (e.g., Add, Configure, Remove).
   - Uses dialog boxes for user input and interaction.

7. Panels and Dialogs:
   - Uses JOptionPane for dialogs.
   - Custom JPanel for each device configuration.

8. Swing GUI Components:
   - JButton: For interactive buttons.
   - JPanel: For layout and custom components.
   - JList: To display device lists.
   - JSlider: For adjustable properties like brightness and speed.

Summary of Flow:
1. Main window opens with action buttons.
2. User can add, configure, or remove devices.
3. Properties can be set based on device type.
4. Devices are stored in an ArrayList.
5. GUI is designed for easy interaction and management.

Design Choices:
- Abstract Class for shared behavior.
- JPanel and JOptionPane for GUI interaction.
- Centralized Button Listener for managing actions.
- Extensible design for adding more device types and features.

How to Use:
1. Run the Project class.
2. Click "Add Device", choose type, and enter name.
3. Click "Configure Device" to modify attributes.
4. Click "Remove Device" to delete a device.
5. Click "Exit" to close the application.

Conclusion:
This application provides a modular, scalable platform for managing smart devices.
Its intuitive interface makes it a great foundation for advanced smart home solutions.
