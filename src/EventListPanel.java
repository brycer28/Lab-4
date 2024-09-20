import javax.swing.*;
import java.awt.*;
import java.util.*;

public class EventListPanel extends JPanel {
    private ArrayList<Event> events = new ArrayList<>();
    private JPanel controlPanel = new JPanel();
    private JPanel displayPanel = new JPanel();
    private JComboBox sortDropDown;
    private JCheckBox filterDisplay;
    private JButton addEventButton;

    //create an eventslistpanel
    public EventListPanel() {
        //basic set up
        this.setPreferredSize(new Dimension(500, 500));
        this.setLayout(new BorderLayout());
        this.setBackground(Color.GRAY);

        //define control panel
        controlPanel.setBackground(Color.DARK_GRAY);
        controlPanel.setPreferredSize(new Dimension(250, 500));

        //create add event button and assign action listener
        addEventButton = new JButton("Add Event");
        JFrame parentFrame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
        addEventButton.addActionListener(e -> {
            //create dialog modal
            AddEventModal dialog = new AddEventModal(parentFrame);
            dialog.setVisible(true);

            String[] results = dialog.getResults();

            //WRITE LOGIC TO ADD AN EVENT
            //ADD FIELD TO RESULTS TO DISTINGUISH MEETING/DEADLINE
        });

        //draw event panels to display panel
        for (Event e : events) {
            EventPanel event = new EventPanel(e);
            displayPanel.add(event);
        }

        //add components to eventlistpanel
        this.add(controlPanel, BorderLayout.WEST);
        this.add(displayPanel, BorderLayout.EAST);
    }

}
