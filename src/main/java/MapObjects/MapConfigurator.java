package MapObjects;

import MoveManaging.*;
import StationObjects.*;

import java.util.ArrayList;

public class MapConfigurator {
    public static IMoveManager moveManager;
    public static Map generateTestMap1(){
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
        map.reserveTicketOffice = new TicketOffice(new Position(0, 8), moveManager);
        return map;
    }
    public static Map generateTestMap2(){
        ArrayList<TicketOffice> offices = new ArrayList<>();
        var map = new Map();
        moveManager = new MoveManager(map);
        offices.add(new TicketOffice(new Position(0, 6), moveManager));
        ArrayList<Spot> entrances = new ArrayList<>();
        entrances.add(new Spot(new Position(7,0)));
        map.setOffices(offices);
        map.setSpots(entrances);
        map.reserveTicketOffice = new TicketOffice(new Position(0, 8), moveManager);
        return map;
    }

    public static Map createMap(MapConfig config){

        ArrayList<TicketOffice> offices = new ArrayList<>();
        var map = new Map();
        moveManager = new MoveManager(map);
        for (var office: config.ticketOffices) {
         offices.add(new TicketOffice(office, moveManager, config.processingTime));
        }
        ArrayList<Spot> entrances = new ArrayList<>();
        for (var entrance: config.entrances) {
            entrances.add(new Spot(entrance));
        }
        map.setOffices(offices);
        map.setSpots(entrances);
        map.reserveTicketOffice = new TicketOffice(config.reservedTicketOffice, moveManager, config.processingTime);
        return map;
    }

    public static IMoveManager getMoveManager(){
        return moveManager;
    }
}
