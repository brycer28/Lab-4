import java.time.LocalDateTime;
import java.util.*;

abstract class
Event implements Comparable<Event> {
    private String name;
    private LocalDateTime dateTime;

    //get name of this event
    public String getName() {return this.name;}

    //get startDateTime of this event
    public LocalDateTime getDateTime() {
        if (this.dateTime != null) {
            return this.dateTime;
        } else {
            this.dateTime = LocalDateTime.now();
            return this.dateTime;
        }
    };

    //set name of this event
    public void setName(String name){
        this.name = name;
    };

    //set startDateTime of this event
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    };

    //return integer value depicting if an event is before/after another event
    public int compareTo(Event e){
        LocalDateTime dateTime = this.getDateTime();
        LocalDateTime other = e.getDateTime();
        return dateTime.compareTo(other);
    };
}