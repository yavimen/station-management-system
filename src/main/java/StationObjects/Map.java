package StationObjects;
import MoveManaging.IMoveManager;

import java.util.*;

public class Map {
    public final int mapSize = 15;

    public TicketOffice reserveTicketOffice;

    public Map(ArrayList<TicketOffice> offices, ArrayList<Spot> spots)
    {
        this.offices = offices;
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

    public Boolean isFreePosition(Position position){
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
