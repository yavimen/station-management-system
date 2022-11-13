package StationObjects;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public class Map {
    public final int mapSize = 15;
    public Map(ArrayList<TicketOffice> offices, ArrayList<Spot> spots)
    {
        this.offices = offices;
        StartAllOffices();//запуск усіх потоків кас

        this.spots = spots;
        this.people = new LinkedList<Chel>();
    }

    public Map(){
        this.people = new LinkedList<Chel>();
    }
    protected ArrayList<Spot> spots;

    public ArrayList<Spot> getSpots()
    {
        return this.spots;
    }
    public void setSpots(ArrayList<Spot> spots) { this.spots = spots; }

    protected ArrayList<TicketOffice> offices;

    public ArrayList<TicketOffice> getOffices()
    {
        return this.offices;
    }
    public void setOffices(ArrayList<TicketOffice> ticketOffices) { this.offices = ticketOffices; }

    protected LinkedList<Chel> people;

    public LinkedList<Chel> getPeople()
    {
        return this.people;
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
        office.AddToQueue(chel);
        //System.out.println("New Notify of adding chel with id= " + chel.id);
        //синхронізацію переніс у клас MoveManager
        synchronized (office)
        {
            if(office.getState().toString() != "RUNNABLE")//тут може бути проблема із notify
            {
                office.notify();//будимо потік якщо він спить
            }
        }

        //System.out.println("Active threads:" + Thread.activeCount());
    }
    public void StartAllOffices()
    {
        for (var office:offices) {
            office.start();
        }
    }

    //для перевірки чи позиція зайнята на мапі
    public Boolean IsFreePosition(Position position){
        var boolList = new LinkedList<Boolean>();

        boolList.add(
          people.stream().anyMatch(c -> c.targetPosition.equals(position))
        );

        boolList.add(
          offices.stream().anyMatch(o -> o.position.equals(position))
        );

        boolList.add(
          spots.stream().anyMatch(s -> s.position.equals(position))
        );
        if(boolList.stream().anyMatch(v -> v.equals(true)))
            return false;

        return true;
    }
}
