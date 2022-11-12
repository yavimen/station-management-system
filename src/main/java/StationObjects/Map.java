package StationObjects;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public class Map {
    public Map(ArrayList<TicketOffice> offices, ArrayList<Spot> spots)
    {
        this.offices = offices;
        StartAllOffices();//запуск усіх потоків кас

        this.spots = spots;
        this.people = new LinkedList<Chel>();
    }

    protected ArrayList<Spot> spots;

    public ArrayList<Spot> getSpots()
    {
        return new ArrayList<>(this.spots);
    }

    protected ArrayList<TicketOffice> offices;

    public ArrayList<TicketOffice> getOffices()
    {
        return new ArrayList<>(this.offices);
    }

    protected LinkedList<Chel> people;

    public LinkedList<Chel> getPeople()
    {
        return new LinkedList<>(this.people);
    }

    public void addPerson(Chel chel)
    {
        var isChelExist = people.stream()
                .anyMatch(c->c.id.equals(chel.id));
        if(!isChelExist)
            people.add(chel);
    }

    public void deletePerson(Chel chel)
    {
        var isChelExist = people.stream()
                .anyMatch(c->c.id.equals(chel.id));
        if(isChelExist)
            people.remove(chel);
    }

    public void AddChelToOfficeQueue(TicketOffice office, Chel chel)
    {
        synchronized (office)
        {
            if(office.getState().toString() != "RUNNABLE")//тут може бути проблема із notify
            {
                office.notify();//будимо потік якщо він спить
            }
        }

        System.out.println("New Notify of adding chel with id= " + chel.id);

        CompletableFuture.runAsync(()->{
            office.AddToQueue(chel);
        });

        System.out.println("Active threads:" + Thread.activeCount());
    }
    private void StartAllOffices()
    {
        for (var office:offices) {
            office.start();
        }
    }
}
