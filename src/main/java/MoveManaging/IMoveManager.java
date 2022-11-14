package MoveManaging;

import StationObjects.*;

import java.util.LinkedList;

public interface IMoveManager {
    void putChelInQueue(TicketOffice office, Chel chel);
    void removeChelFromQueue(TicketOffice office, Chel chel, LinkedList<Chel> newQueue);
}
