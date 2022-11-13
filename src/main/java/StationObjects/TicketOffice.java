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

    //метод AddToQueue має викликатися асинхронно!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void AddToQueue(Chel chel) {

        //synchronized (queue)
        //{
            var isChelExist = queue.stream().anyMatch(c -> c.id.equals(chel.id));
            if (!isChelExist) queue.add(chel);
        //}
        //StartManageChels();
    }
    private void OutputList()
    {
        for (var chel: queue) {
            System.out.println(chel.id + ", " + chel.name);
        }
    }
    @Override
    public void run() {
        try {
            while(true){
                IsManaging = true;
                while(queue.size() > 0)
                {
                    sleep(2000);//час встановити інший
                    System.out.println("String " + queue.getFirst() + " removed!, number: " + queue.size());
                    var person = queue.getFirst();
                    synchronized (queue) {
                        queue.removeFirst();
                    }
                    //видаляю чела з мапи та переміщую всю чергу ближче до каси
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
