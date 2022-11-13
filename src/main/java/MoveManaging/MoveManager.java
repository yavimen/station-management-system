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
    @Override
    public void PutChelInQueue(TicketOffice office, Chel chel) {
        synchronized (office.getQueue()) {
            chel.targetPosition = FindTargetPosition(office);
            chel.isMoved = true;
            map.addPerson(chel);

            map.AddChelToOfficeQueue(office, chel);
        }
        Logger.GetInstance().WriteToFile("Create person "+chel.name+" on Pos("+chel.position.x+", "+chel.position.y+") "
            +"targetPos("+chel.targetPosition.x+", "+chel.targetPosition.y+")");

    }

    // використовують багато потоків
    // викликають каси, при обробці чела в черзі, щоб коректно видалити його з мапи і посунути чергу
    // бажано запускати асинхронно з синхронізацією на мапі перед обрахуноком позиції
    @Override
    public void RemoveChelFromQueue(TicketOffice office ,Chel chel, LinkedList<Chel> newQueue) {
        synchronized (office.getQueue()){
            map.deletePerson(chel);
            Logger.GetInstance().WriteToFile("Remove person "+chel.name+" from Pos "+ chel.targetPosition
                    +" from queue near ticket office on Pos "+chel.office.position.toString());
            var newPersonPosition = chel.targetPosition;
            var xIncrement = office.position.x.equals(0) ? 1 : -1;
            for (var p: newQueue) {
                p.position = p.targetPosition;
                p.targetPosition = new Position(newPersonPosition.x, newPersonPosition.y);
                Logger.GetInstance().WriteToFile("Move person "+p.name+" from Pos"+p.position+" "
                        +"to Pos "+p.targetPosition);
                newPersonPosition.x = newPersonPosition.x + xIncrement;
            }
        }
    }

    //метод для знаходження коректної позиції біля каси
    public Position FindTargetPosition(TicketOffice office){
        Integer xIncrement = 0;
        xIncrement = office.position.x.equals(0) ? 1 : -1;

        var startPosition = new Position(office.position.x + xIncrement, office.position.y);

        while(!startPosition.x.equals((map.mapSize - 1) / 2)){
            if(map.IsFreePosition(startPosition).equals(true)){
                return startPosition;
            }
            startPosition.x+=xIncrement;
        }
        return null;
    }
}
