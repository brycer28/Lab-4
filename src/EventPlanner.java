import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class EventPlanner {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));

        EventListPanel eventListPanel = new EventListPanel();

        frame.add(eventListPanel);


        frame.setVisible(true);
        frame.pack();













//        LocalDateTime deadline = LocalDateTime.of(2024, 12, 7, 15, 0);
//        Deadline d = new Deadline("Lab 2", deadline);
//        EventPanel panel = new EventPanel(d);
//
//        LocalDateTime deadline2 = LocalDateTime.of(2024, 12, 15, 15, 0);
//        Deadline d2 = new Deadline("Lab 3", deadline);
//        EventPanel panel2 = new EventPanel(d2);
//        panel2.setBackground(Color.green);
//
//        LocalDateTime d3start = LocalDateTime.of(2024, 12, 22, 15, 0);
//        LocalDateTime d3end = LocalDateTime.of(2024, 12, 22, 16, 0);
//        String location = "MCS 338";
//        Meeting d3 = new Meeting("Office Hrs", d3start, d3end, location);
//        EventPanel panel3 = new EventPanel(d3);
//        panel3.setBackground(Color.red);
//
//        //temporary container
//        JPanel container = new JPanel();
//        container.setLayout(new GridLayout(1, 1));
//
//        container.add(panel);
//        container.add(panel2);
//        container.add(panel3);
//
//        frame.add(new JScrollPane(container));


        //create new EventListPanel
    }
}
