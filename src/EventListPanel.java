import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.time.*;
import java.util.*;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events = new ArrayList<>();
    private JPanel controlPanel = new JPanel();
    private JPanel displayPanel = new JPanel();
    private JComboBox sortDropDown;
    private JCheckBox checkBox1 = new JCheckBox("Completed",true);
    private JCheckBox checkBox2 = new JCheckBox("Deadline", true);
    private JCheckBox checkBox3 = new JCheckBox("Meeting", true);
    private JButton addEventButton;

    /*
    EVENTLISTPANEL has a DISPLAY and CONTROL PANEL

    DISPLAYPANEL has access to a list of EVENTPANELS which may be sorted and/or filtered

    CONTROLPANEL has a button -> modal, a JComboBox for A-Z/Z-A sorting, and JCheckBoxes for filtering list
     */

    //create an eventslistpanel
    public EventListPanel() {
        //basic set up
        this.setPreferredSize(new Dimension(500, 650));
        this.setBackground(Color.GRAY);

        //define control panel
        controlPanel.setBackground(Color.DARK_GRAY);
        controlPanel.setPreferredSize(new Dimension(250, 650));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        //define display panel
        displayPanel.setBackground(Color.GRAY);
        displayPanel.setPreferredSize(new Dimension(450, 650));
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));

        //create panels to hold components
        JPanel buttonPanel = new JPanel();
        JPanel comboBoxPanel = new JPanel();
        JPanel checkBoxPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);
        comboBoxPanel.setBackground(Color.GRAY);
        checkBoxPanel.setBackground(Color.GRAY);
        checkBoxPanel.setLayout(new GridLayout(1, 3));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        comboBoxPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        checkBoxPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        //create controlPanel components
        sortDropDown = new JComboBox<>(new String[]{"Name", "RName", "Date", "RDate"});
        addEventButton = new JButton("Add Event");

        //add event listener to "ADD EVENT" button
        //adding new event should clear displaypanel, sort arraylist, and re-add all event
        JFrame parentFrame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        addEventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //create dialog modal
                AddEventModal dialog = new AddEventModal(parentFrame);
                dialog.setVisible(true);

                //get information from jdialog
                String[] results = dialog.getResults();

                //check that results exist
                if (results == null) {
                    System.out.println("No results found");
                    return;
                }

                //boolean to determine if event is meeting
                Boolean isMeeting = results[0].equals("Meeting");
                String name = results[1];
                LocalDate date = LocalDate.parse(results[2]);
                LocalTime startTime = LocalTime.parse(results[3]);
                LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
                LocalTime endTime;
                LocalDateTime endDateTime;
                String location;

                //store data from results to create event depending on event type
                if (!isMeeting) {
                    Deadline deadline = new Deadline(name, startDateTime);
                    events.add(deadline);
                } else {
                    endTime = LocalTime.parse(results[4]);
                    location = results[5];
                    endDateTime = LocalDateTime.of(date, endTime);
                    Meeting meeting = new Meeting(name, startDateTime, endDateTime, location);
                    events.add(meeting);
                }
                updateDisplayPanel();
            }
        });

        //add action listener to JComboBox
        //will sort and re-display events in correct order
        sortDropDown.addActionListener(e -> {
            updateDisplayPanel();
        });

        //update each time filters are clicked
        checkBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDisplayPanel();
            }
        });
        checkBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDisplayPanel();
            }
        });
        checkBox3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDisplayPanel();
            }
        });

        //add components to respective panels
        buttonPanel.add(addEventButton);
        comboBoxPanel.add(sortDropDown);
        checkBoxPanel.add(checkBox1);
        checkBoxPanel.add(checkBox2);
        checkBoxPanel.add(checkBox3);

        //create note panel to display at bottom of control panel
        //will explain some features that don't fully work
        JPanel notePanel = new JPanel();
        notePanel.setBackground(Color.GRAY);
        notePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        notePanel.setLayout(new BorderLayout());
        JLabel noteLabel = new JLabel("Read README.md on GitHub please");
        notePanel.add(noteLabel, BorderLayout.CENTER);

        //add sub-panels to control panel
        controlPanel.add(buttonPanel);
        controlPanel.add(Box.createVerticalStrut(8));
        controlPanel.add(comboBoxPanel);
        controlPanel.add(Box.createVerticalStrut(8));
        controlPanel.add(checkBoxPanel);
        controlPanel.add(Box.createVerticalStrut(8));
        controlPanel.add(notePanel);

        //temporary test events
        LocalDateTime currentDateTime = LocalDateTime.now();
        Deadline rightNow = new Deadline("Example Deadline 1", currentDateTime);
        EventPanel nowPanel = new EventPanel(rightNow);

        LocalDateTime deadline = LocalDateTime.of(2024, 10, 15, 13, 0);
        Deadline d = new Deadline("Example Deadline 2", deadline);
        EventPanel panel = new EventPanel(d);

        LocalDateTime deadline2 = LocalDateTime.of(2024, 9, 27, 12, 0);
        Deadline d2 = new Deadline("Example Deadline 3", deadline2);
        EventPanel panel2 = new EventPanel(d2);

        LocalDateTime d3start = LocalDateTime.of(2024, 10, 22, 10, 0);
        LocalDateTime d3end = LocalDateTime.of(2024, 10, 22, 11, 0);
        String location = "UCA";
        Meeting d3 = new Meeting("Example Meeting: Interview", d3start, d3end, location);
        EventPanel panel3 = new EventPanel(d3);

        //add preset events
        events.add(rightNow);
        events.add(d);
        events.add(d2);
        events.add(d3);
        updateDisplayPanel();

        //create JScrollPane for displayPanel
        JScrollPane scrollPane = new JScrollPane(displayPanel);

        //add components to eventlistpanel
        this.add(controlPanel);
        this.add(scrollPane);
    }

    //refresh the displayPanel to properly match filters or sorting option
    public void updateDisplayPanel() {
        //clear display so each may be redisplayed properly
        displayPanel.removeAll();

        //sort events list before displaying
        sortEvents();

        //for each event, check if it is completable, and check its filters
        for (Event event : events) {
            //if not instance of completable, or filtered out
            //print event to console and DO NOT DISPLAY
            if (!(event instanceof Completable)) {
                System.out.println(event+" is filtered. NEXT!");
                continue;
            } else if (!filterEvent(event)) {
                System.out.println(event+" is filtered. NEXT!");
                continue;
            }
            //create an event panel and update its urgency
            EventPanel eventPanel = new EventPanel(event);
            eventPanel.setPreferredSize(new Dimension(displayPanel.getWidth(), 80));
            eventPanel.updateUrgency();

            //add spacer then add event panel
            displayPanel.add(Box.createVerticalStrut(8));
            displayPanel.add(eventPanel);
        }
        repaint();
        revalidate();
    }

    //filter out unwanted events, return true if event should be displayed
    public boolean filterEvent(Event event) {
        //return boolean for if event would be filtered
        boolean isFiltered = false;
        //check if event is complete, meeting, or deadline
        if (checkBox1.isSelected()) {
            if (((Completable) event).isComplete()) {
                isFiltered = true;
            }
        }
        if (checkBox2.isSelected()) {
            if (!((Completable) event).isComplete() && event instanceof Deadline) {
                isFiltered = true;
            }
        }
        if (checkBox3.isSelected()) {
            if (!((Completable) event).isComplete() && event instanceof Meeting) {
                isFiltered = true;
            }
        }
        return isFiltered;
    }

    //sort the list of events based on the state of the JComboBox
    public void sortEvents() {
        String choice = (String) sortDropDown.getSelectedItem();

        //sort events either name order, reverse name, date order, or reverse date
        if (choice != null) {
            switch (choice) {
                case "Name":
                    Collections.sort(events, Comparator.comparing(Event::getName));
                    break;
                case "Name-Reverse":
                    Collections.sort(events, Comparator.comparing(Event::getName));
                    Collections.reverse(events);
                    break;
                case "Date":
                    Collections.sort(events, Comparator.comparing(Event::getDateTime));
                    break;
                case "Date-Reverse":
                    Collections.sort(events, Comparator.comparing(Event::getDateTime));
                    Collections.reverse(events);
                    break;
            }
        }
        revalidate();
        repaint();
    }
}