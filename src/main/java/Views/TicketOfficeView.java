package Views;

import StationObjects.Position;
import StationObjects.TicketOffice;

public class TicketOfficeView {
    public Position position;
    public Boolean isManaging;
    public TicketOfficeView(TicketOffice office){
        this.position = new Position(office.position.x,office.position.y);
        this.isManaging = office.getIsManaging();
    }
}
