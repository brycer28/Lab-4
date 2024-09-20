import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel {
    private Event event;
    private JButton completeButton; //only if implements completable

    //create event panel
    public EventPanel(Event event) {
        this.event = event;
        this.setPreferredSize(new Dimension(100, 100));
        this.setBackground(Color.CYAN);
    }

    //overrridden method to correctly display event information
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("SansSerif", Font.BOLD, 15 ));
        g.setColor(Color.BLACK);

        //get text metrics for centering within panel
        FontMetrics fm = g.getFontMetrics();
        int panelWidth = this.getWidth();
        int y = 30; //starting Y pos

        //drawing logic if deadline
        if (event != null) {
            //draw name of Event
            String name = event.getName();
            int nameWidth = fm.stringWidth(name);
            g.drawString(name, (panelWidth - nameWidth) / 2, y);
            y += fm.getHeight() + 5;

            //separate date and time (to put on separate lines)
            String date = event.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            String[] splitDate = date.split(" ");
            date = splitDate[0];
            String time = splitDate[1];

            //draw date of Event
            int dateWidth = fm.stringWidth(date);
            g.drawString(date, (panelWidth - dateWidth) / 2, y);
            y += fm.getHeight() + 5;

            //separating the time logic of deadline and meeting (HH:mm)
            if (event instanceof Deadline) {
                //draw time of Deadline
                int timeWidth = fm.stringWidth(time);
                g.drawString(time, (panelWidth - timeWidth) / 2, y);
                y += fm.getHeight() + 5;
            }

            //if event is meeting, display start and end time (HH:mm - HH:mm (DURATION))
            if (event instanceof Meeting) {
                //get string of meeting time info (startTime - endTime (duration))
                String startTime = time;
                String endTime = ((Meeting) event).getEndDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                Duration duration = ((Meeting) event).getDuration();
                String meetingInfo = startTime + " - " + endTime + "(" + duration + ")";

                //draw meeting time info
                int meetingWidth = fm.stringWidth(meetingInfo);
                g.drawString(meetingInfo, (panelWidth - meetingWidth) / 2, y);
                y += fm.getHeight() + 5;

                //draw location of meeting
                String location = ((Meeting) event).getLocation();
                int locationWidth = fm.stringWidth(location);
                g.drawString(location, (panelWidth - locationWidth) / 2, y);
                y += fm.getHeight() + 5;
            }
        }
    }

    public void updateUrgency() {
        //set background color according to urgency of Event
    }
}


