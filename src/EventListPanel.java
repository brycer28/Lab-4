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
    private JCheckBox checkBox1 = new JCheckBox("Overdue",true);
    private JCheckBox checkBox2 = new JCheckBox("Due Soon", true);
    private JCheckBox checkBox3 = new JCheckBox("Due Later", true);
    private JButton addEventButton;

    /*
    EVENTLISTPANEL has a DISPLAY and CONTROL PANEL

    DISPLAYPANEL has access to a list of EVENTPANELS which may be sorted and/or filtered

    CONTROLPANEL has a button -> modal, a JComboBox for A-Z/Z-A sorting, and JCheckBoxes for filtering list
     */

    //create an eventslistpanel
    public EventListPanel() {
        //basic set up
        this.setPreferredSize(new Dimension(500, 500));
        this.setBackground(Color.GRAY);

        //define control panel
        controlPanel.setBackground(Color.DARK_GRAY);
        controlPanel.setPreferredSize(new Dimension(250, 500));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        //define display panel
        displayPanel.setBackground(Color.GRAY);
        displayPanel.setPreferredSize(new Dimension(450, 500));
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
        sortDropDown = new JComboBox<>(new String[]{"Chronological", "Reverse"});
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
                System.out.println(Arrays.toString(results));

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
        //NOTE: I wanted to make this into a sortEvents() function so that
        // it could be called when new events are added, but I could not
        // find a solution that retains the lambda function
        sortDropDown.addActionListener(e -> {
            sortEvents();
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

        //add sub-panels to control panel
        controlPanel.add(buttonPanel);
        controlPanel.add(Box.createVerticalStrut(8));
        controlPanel.add(comboBoxPanel);
        controlPanel.add(Box.createVerticalStrut(8));
        controlPanel.add(checkBoxPanel);

        //temporary test events
        LocalDateTime currentDateTime = LocalDateTime.now();
        Deadline rightNow = new Deadline("NOW!!!", currentDateTime);
        EventPanel nowPanel = new EventPanel(rightNow);

        LocalDateTime deadline = LocalDateTime.of(2024, 12, 15, 13, 0);
        Deadline d = new Deadline("Assembly Lab 1", deadline);
        EventPanel panel = new EventPanel(d);

        LocalDateTime deadline2 = LocalDateTime.of(2024, 12, 15, 12, 0);
        Deadline d2 = new Deadline("Cal 1 HW", deadline2);
        EventPanel panel2 = new EventPanel(d2);

        LocalDateTime d3start = LocalDateTime.of(2024, 12, 22, 10, 0);
        LocalDateTime d3end = LocalDateTime.of(2024, 12, 22, 11, 0);
        String location = "MCS 338";
        Meeting d3 = new Meeting("Office Hrs", d3start, d3end, location);
        EventPanel panel3 = new EventPanel(d3);

        events.add(rightNow);
        events.add(d);
        events.add(d2);
        events.add(d3);
        updateDisplayPanel();

        //add components to eventlistpanel
        this.add(controlPanel);
        this.add(displayPanel);
    }

    //function to re-display events when a new one is added/removed
    //or if the order/filters are changed
//    public void updateDisplayPanel() {
//        displayPanel.removeAll();
//
//        //must create a copy arrayList so that filters don't fully delete events
//        sortEvents();
//        //for each event in events, redraw the event panel
//        for (Event event : events) {
//            //ensure that event is instantiated and completable
//            if (event instanceof Meeting || event instanceof Deadline) {
//                //if event is not marked complete
//                if (!((Completable) event).isComplete()){
//                    //if the events corresponding filter is checked, it may be displayed
//                    if (filterEvent(event)) {
//                        //create an event panel and update its urgency
//                        EventPanel eventPanel = new EventPanel(event);
//                        eventPanel.setPreferredSize(new Dimension(displayPanel.getWidth(), 100));
//                        eventPanel.updateUrgency();
//
//                        //add spacer then add event panel
//                        displayPanel.add(Box.createVerticalStrut(8));
//                        displayPanel.add(eventPanel);
//                    }
//                } else {
//                    System.out.println(event+" is filtered. NEXT!");
//                }
//            } else {
//                System.out.println("ERR BAD GRR!");
//            }
//        }
//        repaint();
//        revalidate();
//    }

    public void updateDisplayPanel() {
        displayPanel.removeAll();

        //must create a copy arrayList so that filters don't fully delete events
        sortEvents();
        for (Event event : events) {
            //if not instance of completable, marked complete, or filtered out
            //print event to console and DO NOT DISPLAY
            if (!(event instanceof Completable)) {
                System.out.println(event+" is filtered. NEXT!");
                continue;
            } else if (((Completable) event).isComplete()) {
                System.out.println(event+" is marked complete. NEXT!");
                continue;
            } else if (!filterEvent(event)) {
                System.out.println(event+" is filtered. NEXT!");
                continue;
            }
            //create an event panel and update its urgency
            EventPanel eventPanel = new EventPanel(event);
            eventPanel.setPreferredSize(new Dimension(displayPanel.getWidth(), 100));
            eventPanel.updateUrgency();

            //add spacer then add event panel
            displayPanel.add(Box.createVerticalStrut(8));
            displayPanel.add(eventPanel);
        }
        repaint();
        revalidate();
    }


    public boolean filterEvent(Event event) {
        //return boolean for if event would be filtered
        boolean isFiltered = false;
        if (checkBox1.isSelected()) {
            if (event.getDateTime().isBefore(LocalDateTime.now())) {
                isFiltered = true;
            }
        }
        if (checkBox2.isSelected()) {
            if (event.getDateTime().isAfter(LocalDateTime.now())
            && event.getDateTime().isBefore(LocalDateTime.now().plusDays(1))) {
                isFiltered = true;
            }
        }
        if (checkBox3.isSelected()) {
            if (event.getDateTime().isAfter(LocalDateTime.now().plusDays(1))) {
                isFiltered = true;
            }
        }
        return isFiltered;
    }

    public void sortEvents() {
        String choice = (String) sortDropDown.getSelectedItem();
        switch (choice) {
            case "Chronological":
                Collections.sort(events, Comparator.comparing(Event::getDateTime));
                break;
            case "Reverse":
                Collections.sort(events, Comparator.comparing(Event::getDateTime).reversed());
                break;
        }
    }
}
