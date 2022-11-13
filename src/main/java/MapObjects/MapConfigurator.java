package MapObjects;

import MoveManaging.*;
import StationObjects.*;

import java.util.ArrayList;

public class MapConfigurator {
    public static IMoveManager moveManager;
    public static Map GenerateTestMap1(){
        ArrayList<TicketOffice> offices = new ArrayList<>();
        var map = new Map();
        moveManager = new MoveManager(map);
        offices.add(new TicketOffice(new Position(0, 6), moveManager));
        offices.add(new TicketOffice(new Position(14, 6), moveManager));
        ArrayList<Spot> entrances = new ArrayList<>();
        entrances.add(new Spot(new Position(7,0)));
        entrances.add(new Spot(new Position(7,14)));
        map.setOffices(offices);
        map.setSpots(entrances);
        return map;
    }

    public static Map CreateMap(MapConfig config){

        ArrayList<TicketOffice> offices = new ArrayList<>();
        var map = new Map();
        moveManager = new MoveManager(map);
        for (var office: config.ticketOffices) {
         offices.add(new TicketOffice(office, moveManager));
        }
        ArrayList<Spot> entrances = new ArrayList<>();
        for (var entrance: config.entrances) {
            entrances.add(new Spot(entrance));
        }
        map.setOffices(offices);
        map.setSpots(entrances);
        return map;
    }

    public static IMoveManager getMoveManager(){
        return moveManager;
    }
}