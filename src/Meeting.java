import java.time.*;
import java.util.*;

public class Meeting extends Event implements Completable {
    private LocalDateTime endDateTime;
    private String location;
    private boolean complete;

    //create a meeting object
    public Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        this.setName(name);
        this.setDateTime(start);
        this.setEndDateTime(end);
        this.setLocation(location);
    }

    //"toggle" the complete boolean, allows meetings to be uncompleted
    public void complete() {
        this.complete = !complete;
    }

    //check if this event is complete
    public boolean isComplete() {
        return this.complete;
    }

    //return the endDateTime of this meeting
    public LocalDateTime getEndDateTime() {return this.endDateTime;}

    //return the duration of this meeting
    public Duration getDuration() {return Duration.between(this.getDateTime(), this.getEndDateTime());}

    //return the location of this meeting
    public String getLocation() {
        return this.location;
    }

    //set endDateTime of this meeting
    public void setEndDateTime(LocalDateTime end) {
        this.endDateTime = end;
    }

    //set location of this meeting
    public void setLocation(String location) {
        this.location = location;
    }
}
