package StationObjects;

import Logging.Logger;
import MoveManaging.IMoveManager;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Random;

public class TicketOffice extends Thread {
    protected IMoveManager moveManager;
    public Position position;

    private TicketOffice reserveTicketOffice;

    public void setReserveTicketOffice(TicketOffice value){ reserveTicketOffice = value; }
    private LinkedList<Chel> queue;
    public LinkedList<Chel> getQueue() {
        return queue;
    }
    private Boolean isManaging = true;// каса закрита/відкрита
    private Boolean isReserve = false;
    public Boolean getIsManaging(){
        return isManaging;
    }
    public Boolean getIsReserve() {return isReserve; }
    //у секундах
    private Integer processingTime;
    private Integer servedChelsNumber = 0;

    public TicketOffice(Position position, IMoveManager moveManager, Boolean isReserve) {
        this.processingTime = 1;
        this.position = position;
        this.queue = new LinkedList<Chel>();
        this.moveManager = moveManager;
        this.isReserve = isReserve;
    }

    public TicketOffice(Position position, IMoveManager moveManager, Integer processingTime, Boolean isReserve) {
        this.position = position;
        this.queue = new LinkedList<Chel>();
        this.moveManager = moveManager;
        this.processingTime = processingTime;
        this.isReserve = isReserve;
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

    private Boolean DisableStation()
    {
        Boolean result = false;
        Random randGenerator = new Random(LocalDateTime.now().getNano());

        if(randGenerator.nextInt(200) < servedChelsNumber && !isReserve)
        {
            Logger.GetInstance().WriteToFile("Station " + this.position.toString() + " disabled!\n");

            result = true;
            isManaging = false;

            synchronized (queue)
            {
                for (var chel: queue) {
                    moveManager.removeChelFromDisabledQueue(this, chel, new LinkedList<>(queue));
                    moveManager.putChelInQueue(reserveTicketOffice, chel);
                    chel.office = reserveTicketOffice;
                }
            }
        }
        return result;
    }
    @Override
    public void run() {
        try {
            while(true){
                while(queue.size() > 0)
                {
                    var person = queue.getFirst();
                    sleep(person.quantity * processingTime*1000);
                    System.out.println("String " + queue.getFirst() + " removed!, number: " + queue.size());
                    synchronized (queue) {
                        queue.removeFirst();
                    }
                    moveManager.removeChelFromQueue(this, person, new LinkedList<>(queue));
                    servedChelsNumber++;

                    if(DisableStation()) {
                        break;
                    }
                }
                synchronized (this) {

                    if(!isManaging)
                    {
                        wait(10000); //чекаємо 10 секунд поки відновиться робота каси
                        Logger.GetInstance().WriteToFile("Station " + this.position.toString() + " enabled!\n");
                        isManaging = true;
                    }
                    wait(); //каса очікує
                }
            }

        }
        catch (InterruptedException e) {
            System.out.println("Ticket office "+this.position+" closed.");
        }
    }
}
