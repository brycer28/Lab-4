import java.time.*;
import java.util.*;

public class Meeting extends Event implements Completable {
    private LocalDateTime endDateTime;
    private String location;
    private boolean complete;

    public Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        this.setName(name);
        this.setDateTime(start);
        this.setEndDateTime(end);
        this.setLocation(location);
    }

    public void complete() {
        this.complete = true;
    }

    public boolean isComplete() {
        return this.complete;
    }

    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    public Duration getDuration() {
        Duration duration = Duration.between(this.getDateTime(), this.getEndDateTime());
        return duration;
    }

    public String getLocation() {
        return this.location;
    }

    public void setEndDateTime(LocalDateTime end) {
        this.endDateTime = end;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
