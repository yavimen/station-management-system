package StationObjects;

import java.util.LinkedList;
public class TicketOffice implements Runnable{
    public TicketOffice(){};
    public TicketOffice(Position position)
    {
        this.position = position;
    }
    public Position position;

    private LinkedList<Chel> queue;

    public void AddToQueue(Chel chel)
    {
        var isChelExist = queue.stream()
                .anyMatch(c->c.id.equals(chel.id));
        if(!isChelExist)
            queue.add(chel);
    }

    @Override
    public void run() {

    }
}
