package MoveManaging;
import Logging.Logger;
import StationObjects.*;

import java.util.LinkedList;

public class MoveManager implements IMoveManager {
    protected Map map;
    public MoveManager(Map map){
        this.map = map;
    }

    // використовує 1 потік
    // для використання потоком, що створює челів
    /*
        дивиться чи чел має особливий статус чи ні, відповідно додає після людини, що вже обслуговується
        у касі або ставить у кінець черги
     */
    @Override
    public void putChelInQueue(TicketOffice office, Chel chel) {
        synchronized (office.getQueue()) {
            chel.isMoved = true;
            if(chel.chelStatus.equals(ChelStatus.Usual) || office.getQueue().size() <= 1) {
                chel.targetPosition = findTargetPosition(office);
                map.addPerson(chel);
                office.addAtTheEndOfQueue(chel);
            }
            else{
                chel.targetPosition = getPositionForNewDisabledPersonNearOffice(office);
                map.addPerson(chel);
                office.addChelToQueueAtPosition(moveQueueForDisabledPersonAndFindInsertingIndex(office), chel);
            }
        }
        Logger.GetInstance().WriteToFile("Create "+chel.chelStatus+" person "+chel.name+" on Pos("+chel.position.x+", "+chel.position.y+") "
                +"targetPos("+chel.targetPosition.x+", "+chel.targetPosition.y+")");
    }

    // використовують багато потоків
    // викликають каси, при обробці чела в черзі, щоб коректно видалити його з мапи і посунути чергу
    // бажано запускати асинхронно з синхронізацією на мапі перед обрахуноком позиції
    @Override
    public void removeChelFromQueue(TicketOffice office , Chel chel, LinkedList<Chel> newQueue) {
        synchronized (office.getQueue()){
            map.deletePerson(chel);
            Logger.GetInstance().WriteToFile("Remove person "+chel.name+" from Pos "+ chel.targetPosition
                    +" from queue near ticket office on Pos "+chel.office.position.toString());
            var newPersonPosition = chel.targetPosition;
            var xIncrement = office.position.x.equals(0) ? 1 : -1;
            Logger.GetInstance().WriteToFile("Move queue near "+office.position+" because "+chel.name+" left.");
            for (var p: newQueue) {
                p.position = p.targetPosition;
                p.targetPosition = new Position(newPersonPosition.x, newPersonPosition.y);
                Logger.GetInstance().WriteToFile("Move person "+p.name+" from Pos"+p.position+" "
                        +"to Pos "+p.targetPosition);
                newPersonPosition.x = newPersonPosition.x + xIncrement;
            }
        }
    }

    public int moveQueueForDisabledPersonAndFindInsertingIndex(TicketOffice office){
        var xIncrement = office.position.x.equals(0) ? 1 : -1;
        var queue = office.getQueue();
        Logger.GetInstance().WriteToFile("Moving the queue near "+office.position+
                " because came person with special status.");
        for(int i = queue.size() - 1; i > 1; --i){
            var p = queue.get(i);
            if(!p.chelStatus.equals(ChelStatus.Usual)){
                return i + 1;
            }
            p.position = p.targetPosition;
            p.targetPosition = new Position(p.targetPosition.x + xIncrement, p.targetPosition.y);
            Logger.GetInstance().WriteToFile("Move person "+p.name+" from Pos"+p.position+" "
                    +"to Pos "+p.targetPosition+".");
        }
        return 1;
    }

    public Position getPositionForNewDisabledPersonNearOffice(TicketOffice office){
        var xIncrement = office.position.x.equals(0) ? 1 : -1;
        var queue = office.getQueue();
        for(int i = queue.size() - 1; i > 1; --i){
            var p = queue.get(i);
            if(!p.chelStatus.equals(ChelStatus.Usual)){
                return new Position(p.targetPosition.x + xIncrement, p.targetPosition.y);
            }
        }
        return new Position(office.position.x + 2 * xIncrement, office.position.y);
    }

    //метод для знаходження коректної позиції біля каси
    public Position findTargetPosition(TicketOffice office){
        int xIncrement = office.position.x.equals(0) ? 1 : -1;

        var startPosition = new Position(office.position.x + xIncrement, office.position.y);

        while(!startPosition.x.equals((map.mapSize - 1) / 2)){
            if(map.isFreePosition(startPosition).equals(true)){
                return startPosition;
            }
            startPosition.x+=xIncrement;
        }
        return null;
    }
}
