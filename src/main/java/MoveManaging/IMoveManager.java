package MoveManaging;

import StationObjects.*;

import java.util.LinkedList;

public interface IMoveManager {
    void PutChelInQueue(TicketOffice office, Chel chel);
    void RemoveChelFromQueue(TicketOffice office,Chel chel, LinkedList<Chel> newQueue);
}
