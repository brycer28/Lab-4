import java.time.LocalDateTime;
import java.util.*;

abstract class
Event implements Comparable<Event> {
    private String name;
    private LocalDateTime dateTime;

    public String getName() {return this.name;}
    public LocalDateTime getDateTime() {return this.dateTime;};

    public void setName(String name){
        this.name = name;
    };

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    };

    public int compareTo(Event e){
        LocalDateTime dateTime = this.getDateTime();
        LocalDateTime other = e.getDateTime();
        return dateTime.compareTo(other);
    };
}