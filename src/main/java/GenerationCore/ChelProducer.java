package GenerationCore;

import GenerationCore.IChelProducer;
import StationObjects.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ChelProducer implements IChelProducer {
    protected Map map;
    private int autoIncrementedId = 0;

    public ChelProducer(Map map) {
        this.map = map;
    }

    public Chel GetChel() {
        var rand = new Random();
        var statuses = ChelStatus.values();
        var names = ReadName();
        var spot = map.getSpots().get(rand.nextInt(map.getSpots().size()));
        var possibleStartPositions = FindTheNearestFreePositions(spot.position);
        var startPosition = possibleStartPositions.get(rand.nextInt(possibleStartPositions.size()));
        var mostSuitableOffice = FindTheMostSuitableTicketOffice(startPosition);

                var newChel = new Chel(autoIncrementedId,
                getRandomPersonStatus().ordinal(),
                rand.nextInt(5) + 1,
                names[rand.nextInt(names.length)],
                mostSuitableOffice,
                spot,
                startPosition
        );

        autoIncrementedId++;

        return newChel;
    }

    private ChelStatus getRandomPersonStatus(){
        Random random = new Random();
        var value = random.nextInt(100);
        if(value < 70){
            return ChelStatus.Usual;
        }
        else if(value < 90){
            return ChelStatus.OldBoy;
        }
        return ChelStatus.Disabled;
    }

    private String[] ReadName() {
        String lines = null;

        try {
            lines = Files.readString(Paths.get("RandomNames.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        var result = lines.split("\\r?\\n");

        return result;
    }

    private TicketOffice FindTheMostSuitableTicketOffice(Position position) {

        //EnabledOffices - всі каси, які можуть приймати клієнтів (не вимкнені) і не є резервними
        var EnabledOffices = map.getOffices().stream().filter(o->o.getIsManaging() == true && o.getIsReserve() == false).toList();
        var mostSuitableOffice = EnabledOffices.get(0);
        var shortestQueueSize = EnabledOffices.get(0).getQueue().size();
        var counter = 0;

        for (var office : EnabledOffices) {
            if (office.getQueue().size() < shortestQueueSize)
            {
                shortestQueueSize = office.getQueue().size();
                mostSuitableOffice = office;
            }
            if (office.getQueue().size() == EnabledOffices.get(0).getQueue().size())
                counter++;
        }

        if (EnabledOffices.size() == counter)
            mostSuitableOffice = FindTheNearestTicketOffice(position, EnabledOffices);

        return mostSuitableOffice;
    }

    private TicketOffice FindTheNearestTicketOffice(Position position, List<TicketOffice> EnabledOffices) {
        var nearestOffice = EnabledOffices.get(0);
        var shortestDistance = FindDistance(position, nearestOffice.position);

        for (var office : EnabledOffices) {
            var dist = FindDistance(position, office.position);
            if (dist < shortestDistance) {
                shortestDistance = dist;
                nearestOffice = office;
            }
        }

        return nearestOffice;
    }

    private List<Position> FindTheNearestFreePositions(Position spotPosition) {
        var availablePositions = new ArrayList<Position>();

        var possiblePositions = new ArrayList<Position>() {
            {
                add(new Position(spotPosition.x, spotPosition.y - 1));
                add(new Position(spotPosition.x + 1, spotPosition.y - 1));
                add(new Position(spotPosition.x + 1, spotPosition.y));
                add(new Position(spotPosition.x + 1, spotPosition.y + 1));
                add(new Position(spotPosition.x, spotPosition.y + 1));
                add(new Position(spotPosition.x - 1, spotPosition.y + 1));
                add(new Position(spotPosition.x - 1, spotPosition.y));
                add(new Position(spotPosition.x - 1, spotPosition.y - 1));
            }
        };

        for (var pos : possiblePositions) {
            if ((pos.x >= 0 && pos.x < map.mapSize) && (pos.y >= 0 && pos.y < map.mapSize) && IsPositionFree(pos))
                availablePositions.add(pos);
        }

        if (availablePositions.size() == 0)
            return new ArrayList<Position>() {
                {
                    add(new Position(0, 0));
                }
            };

        return availablePositions;
    }

    private boolean IsPositionFree(Position position) {
        for (var person : map.getPeople()) {
            if (person.position.equals(position) && person.isMoved == false)
                return false;
        }

        for (var office : map.getOffices()) {
            if (office.position.equals(position))
                return false;
        }

        for (var spot : map.getSpots()) {
            if (spot.position.equals(position))
                return false;
        }

        return true;
    }

    private double FindDistance(Position position1, Position position2) {
        // sqrt((x1 - x2)^2 + (y1 - y2)^2)
        return Math.sqrt(Math.pow(position1.x - position2.x, 2) + Math.pow(position1.y - position2.y, 2));
    }
}
