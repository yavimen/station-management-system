package Views;

import StationObjects.*;

import java.util.LinkedList;

public class MapView {
    public LinkedList<ChelView> people;
    public LinkedList<SpotView> spots;
    public LinkedList<TicketOfficeView> offices;
    public MapView(Map map){
        people = new LinkedList<>();
        offices = new LinkedList<>();
        spots = new LinkedList<>();
        map.getPeople().forEach(c -> people.add(new ChelView(c)));
        map.getOffices().forEach(o -> offices.add(new TicketOfficeView(o)));
        map.getSpots().forEach(o-> spots.add(new SpotView(o)));
    }
}
