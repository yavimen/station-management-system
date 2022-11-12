package StationObjects;

import java.util.LinkedList;

import static java.lang.Thread.sleep;

public class TicketOffice extends Thread {
    public TicketOffice() {
        this.queue = new LinkedList<Chel>();
    }

    public TicketOffice(Position position) {
        this.position = position;
        this.queue = new LinkedList<Chel>();
    }

    public Position position;

    private LinkedList<Chel> queue;

    private Boolean IsManaging = false;

    public LinkedList<Chel> getQueue() {
        return queue;
    }

    //метод AddToQueue має викликатися асинхронно!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void AddToQueue(Chel chel) {

        synchronized (queue)
        {
            var isChelExist = queue.stream().anyMatch(c -> c.id.equals(chel.id));
            if (!isChelExist) queue.add(chel);
        }
        StartManageChels();
    }

    //метод перевіряє чи працює основний потік каси (обробляє челів)
    //якщо працює, то він самостійно опрацює додавання чела і викликати метод опарцювання не треба
    private void StartManageChels()
    {
        if(!IsManaging)
        {
            ManageChelsInQueue();
        }
    }

    //метод суто для дебагу, може напишу пару тестів
    private void OutputList()
    {
        for (var chel: queue) {
            System.out.println(chel.id + ", " + chel.name);
        }
    }
    private void ManageChelsInQueue()
    {
        try {
            IsManaging = true;
            while(queue.size() > 0)
            {
                sleep(1000);//час встановити інший
                System.out.println("String " + queue.getFirst() + " removed!, number: " + queue.size());

                synchronized (queue) {
                    queue.removeFirst();
                }

                OutputList();//потім це видалити
            }
            IsManaging = false;

            synchronized (this)
            {
                wait();
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        //при запуску каси потік синхронізує власну чергу та стає в режим очікування
        synchronized (queue) {
            try {
                queue.wait();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
