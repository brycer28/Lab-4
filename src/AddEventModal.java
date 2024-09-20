import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class AddEventModal extends JDialog {
    //array of strings to hold information for creating an event
    private String[] results = new String[4];

    //create dialog box
    public AddEventModal(Frame parent) {
        //basic set up for dialog
        super(parent, "Add Event", true);
        setSize(250, 200);
        setLocationRelativeTo(parent);

        //create text fields for event input
        JTextField nameField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField startTimeField = new JTextField();
        JTextField endTimeField = new JTextField();
        JTextField locationField = new JTextField();

        //add labels and text fields for information
        JPanel eventOptionsPanel = new JPanel(new GridLayout(4,2));
        eventOptionsPanel.add(new JLabel("Name"));
        eventOptionsPanel.add(nameField);
        eventOptionsPanel.add(new JLabel("Date"));
        eventOptionsPanel.add(dateField);
        eventOptionsPanel.add(new JLabel("Start Time"));
        eventOptionsPanel.add(startTimeField);
        eventOptionsPanel.add(new JLabel("End Time (optional)"));
        eventOptionsPanel.add(endTimeField);
        eventOptionsPanel.add(new JLabel("Location (optional)"));
        eventOptionsPanel.add(locationField);

        //create complete button
        JButton addEvent = new JButton("Add Event");

        //add components to dialog
        getContentPane().add(eventOptionsPanel, BorderLayout.CENTER);
        getContentPane().add(addEvent, BorderLayout.SOUTH);

        //add action listener to event button
        //when pressed, results are stored and dialog closed
        addEvent.addActionListener(e -> {
            results[0] = nameField.getText();
            results[1] = dateField.getText();
            results[2] = startTimeField.getText();
            results[3] = endTimeField.getText();
            results[4] = locationField.getText();
            dispose();
        });
    }

    //returns the private results array so an event can be created
    public String[] getResults() {
        return results;
    }
}
