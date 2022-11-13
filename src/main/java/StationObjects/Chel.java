package StationObjects;

public class Chel {
    public Chel(int id, int status, int quantity, String name, TicketOffice office, Spot spot, Position position) {
        this.id = id;
        this.chelStatus = ChelStatus.values()[status];
        this.quantity = quantity;
        this.name = name;
        this.office = office;
        this.spot = spot;
        this.position = position;
        targetPosition = null;
        isMoved = false;
    }

    public Integer id;
    public ChelStatus chelStatus;
    public Integer quantity;
    public String name;
    public TicketOffice office;
    public Spot spot;
    public Position position;
    public Boolean isMoved;
    public Position targetPosition;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chel) {
            if (((Chel) obj).id.equals(this.id) && ((Chel) obj).position.equals(this.position) && ((Chel) obj).quantity.equals(this.quantity) && ((Chel) obj).office.equals(this.office) && ((Chel) obj).name.equals(this.name) && ((Chel) obj).spot.equals(this.spot) && ((Chel) obj).chelStatus.equals(this.chelStatus))
                return true;
        }
        return false;
    }
}
