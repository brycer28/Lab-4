import java.time.LocalDateTime;

public class Deadline extends Event implements Completable{
    private boolean complete;

    //create a deadline object
    public Deadline(String name, LocalDateTime deadline) {
        this.setName(name);
        this.setDateTime(deadline);
    };

    //"toggle" the complete boolean, allows meetings to be uncompleted
    public void complete() {
        this.complete = !complete;
    }

    //check if deadline is complete
    public boolean isComplete() {
        return this.complete;
    }
}
