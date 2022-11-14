package StationObjects;

import MoveManaging.IMoveManager;

import java.util.LinkedList;

public class TicketOffice extends Thread {
    protected IMoveManager moveManager;
    public TicketOffice(Position position, IMoveManager moveManager) {
        this.position = position;
        this.queue = new LinkedList<Chel>();
        this.moveManager = moveManager;
    }

    public Position position;

    private LinkedList<Chel> queue;

    private Boolean isManaging = false;

    public Boolean getIsManaging(){
        return isManaging;
    }
    public LinkedList<Chel> getQueue() {
        return queue;
    }

    public void addAtTheEndOfQueue(Chel chel)
    {
        var isChelExist = queue.stream().anyMatch(c -> c.id.equals(chel.id));
        if (!isChelExist) queue.add(chel);

        synchronized (this)
        {
            if(getState().toString() != "RUNNABLE")
            {
                notify();
            }
        }
    }

    public void addChelToQueueAtPosition(int index , Chel chel){
        var isChelExist = queue.stream().anyMatch(c -> c.id.equals(chel.id));
        if (!isChelExist) queue.add(index, chel);

        synchronized (this)
        {
            if(getState().toString() != "RUNNABLE")
            {
                notify();
            }
        }
    }
    @Override
    public void run() {
        try {
            while(true){
                isManaging = true;
                while(queue.size() > 0)
                {
                    sleep(5000);
                    System.out.println("String " + queue.getFirst() + " removed!, number: " + queue.size());
                    var person = queue.getFirst();
                    synchronized (queue) {
                        queue.removeFirst();
                    }
                    moveManager.removeChelFromQueue(this, person, new LinkedList<>(queue));
                }
                synchronized (this) {
                    wait();
                }
            }

        }
        catch (InterruptedException e) {
            System.out.println("Ticket office "+this.position+" closed.");
        }
    }
}
