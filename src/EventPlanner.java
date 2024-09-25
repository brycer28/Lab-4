import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

/*
TO-DO:
*/

public class EventPlanner {
    public static void main(String[] args) {
        //set up frame
        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));

        //create and add eventsListPanel
        EventListPanel eventListPanel = new EventListPanel();
        frame.add(eventListPanel);

        //ensure that frame is visible
        frame.setVisible(true);
        frame.pack();
    }
}
