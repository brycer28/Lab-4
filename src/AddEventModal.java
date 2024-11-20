import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AddEventModal extends JDialog {
    //array of strings to hold information for creating an event
    private String[] results = new String[6];
    private ArrayList<EventObserver> observers = new ArrayList<>();

    //create dialog box
    public AddEventModal(Frame parent) {
        //basic set up for dialog
        super(parent, "Add Event", true);
        setSize(400, 500);
        setLocationRelativeTo(parent);

        //create panels to hold each field
        JPanel eventTypePanel = new JPanel();
        JPanel namePanel = new JPanel();
        JPanel datePanel = new JPanel();
        JPanel startTimePanel = new JPanel();
        JPanel endTimePanel = new JPanel();
        JPanel locationPanel = new JPanel();

        //create text fields for event input
        JCheckBox deadlineBox = new JCheckBox("Deadline");
        JCheckBox meetingBox = new JCheckBox("Meeting");
        JTextField nameField = new JTextField(15);
        JTextField dateField = new JTextField(15);
        JTextField startTimeField = new JTextField(15);
        JTextField endTimeField = new JTextField(15);
        JTextField locationField = new JTextField(15);

        //create JLabels for each event input
        JLabel eventTypeLabel = new JLabel("Event Type");
        JLabel nameLabel = new JLabel("Name");
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD)");
        JLabel startTimeLabel = new JLabel("Start Time");
        JLabel endTimeLabel = new JLabel("End Time");
        JLabel locationLabel = new JLabel("Location");

        //create button group for event type checkboxes
        ButtonGroup group = new ButtonGroup();
        group.add(deadlineBox);
        group.add(meetingBox);
        deadlineBox.setSelected(true); //ensures one box is always set

        //add text fields and labels to panels
        eventTypePanel.add(eventTypeLabel);
        eventTypePanel.add(deadlineBox);
        eventTypePanel.add(meetingBox);
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        datePanel.add(dateLabel);
        datePanel.add(dateField);
        startTimePanel.add(startTimeLabel);
        startTimePanel.add(startTimeField);
        endTimePanel.add(endTimeLabel);
        endTimePanel.add(endTimeField);
        locationPanel.add(locationLabel);
        locationPanel.add(locationField);

        //add all sub-panels to eventOptionsPanel
        JPanel eventOptionsPanel = new JPanel(new GridLayout(6,1));
        eventOptionsPanel.add(eventTypePanel);
        eventOptionsPanel.add(namePanel);
        eventOptionsPanel.add(datePanel);
        eventOptionsPanel.add(startTimePanel);
        eventOptionsPanel.add(endTimePanel);
        eventOptionsPanel.add(locationPanel);

        //create complete button
        JButton addEvent = new JButton("Add Event");

        //add action listener to event button
        //when pressed, results are stored and dialog closed
        addEvent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (deadlineBox.isSelected()) {
                    results[0] = "Deadline";
                    results[1] = nameField.getText();
                    results[2] = dateField.getText();
                    results[3] = startTimeField.getText();
                } else if (meetingBox.isSelected()) {
                    results[0] = "Meeting";
                    results[1] = nameField.getText();
                    results[2] = dateField.getText();
                    results[3] = startTimeField.getText();
                    results[4] = endTimeField.getText();
                    results[5] = locationField.getText();
                }
                Event event = EventFactory.createEvent(results);
                notifyObservers(event);
                dispose();
            }
        });

        //add components to dialog
        getContentPane().add(eventOptionsPanel, BorderLayout.CENTER);
        getContentPane().add(addEvent, BorderLayout.SOUTH);
        setSize(400, 500);
        setLocationRelativeTo(parent);
    }

    //returns the private results array so an event can be created
    public String[] getResults() {
        return results;
    }

    public void addObserver(EventObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(Event event) {
        for (EventObserver observer : observers) {
            observer.onEventAdded(event);
        }
    }
}
