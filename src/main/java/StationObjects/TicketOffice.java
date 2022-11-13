package StationObjects;

import MoveManaging.IMoveManager;

import java.util.LinkedList;

import static java.lang.Thread.sleep;

public class TicketOffice extends Thread {
    public TicketOffice() {
        this.queue = new LinkedList<Chel>();
    }
    protected IMoveManager moveManager;
    public TicketOffice(Position position, IMoveManager moveManager) {
        this.position = position;
        this.queue = new LinkedList<Chel>();
        this.moveManager = moveManager;
    }

    public Position position;

    private LinkedList<Chel> queue;

    private Boolean IsManaging = false;

    public Boolean getIsManaging(){
        return IsManaging;
    }
    public LinkedList<Chel> getQueue() {
        return queue;
    }

    public void AddToQueue(Chel chel)
    {
        var isChelExist = queue.stream().anyMatch(c -> c.id.equals(chel.id));
        if (!isChelExist) queue.add(chel);
    }

    @Override
    public void run() {
        try {
            while(true){
                IsManaging = true;
                while(queue.size() > 0)
                {
                    sleep(2000);
                    System.out.println("String " + queue.getFirst() + " removed!, number: " + queue.size());
                    var person = queue.getFirst();
                    synchronized (queue) {
                        queue.removeFirst();
                    }
                    moveManager.RemoveChelFromQueue(this, person, new LinkedList<>(queue));
                }
                synchronized (this) {
                    wait();
                }
            }

        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
