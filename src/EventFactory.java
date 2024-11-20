import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventFactory {
    public static Event createEvent(String[] eventData) {
        String eventType = eventData[0];
        String name = eventData[1];
        LocalDate date = LocalDate.parse(eventData[2]);
        LocalTime startTime = LocalTime.parse(eventData[3]);
        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);

        if (eventType.equals("Deadline")) {
            return new Deadline(name, startDateTime);
        } else if (eventType.equals("Meeting")) {
            LocalDateTime endDateTime = LocalDateTime.parse(eventData[4]);
            String location = eventData[5];
            return new Meeting(name, startDateTime, endDateTime, location);
        }
        return null;
    }

}
