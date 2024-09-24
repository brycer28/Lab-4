import java.time.LocalDateTime;

public class Deadline extends Event implements Completable{
    private boolean complete;

    //create a deadline object
    public Deadline(String name, LocalDateTime deadline) {
        this.setName(name);
        this.setDateTime(deadline);
    };

    //mark this deadline complete
    public void complete() {
        this.complete = true;
    }

    //check if deadline is complete
    public boolean isComplete() {
        return this.complete;
    }
}
