package StationObjects;
import java.util.*;

public class Map {
    public Map(ArrayList<TicketOffice> offices, ArrayList<Spot> spots)
    {
        this.offices = offices;
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
}
