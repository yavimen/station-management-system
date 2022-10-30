package StationObjects;

public class Chel {
    public Chel(int id, Status status, int quantity, String name,
                TicketOffice office, Spot spot, Position position)
    {
        this.id = id;
        this.status = status;
        this.quantity = quantity;
        this.name = name;
        this.office = office;
        this.spot = spot;
        this.position = position;
    }
    public Integer id;
    public Status status;
    public Integer quantity;
    public String name;
    public TicketOffice office;
    public Spot spot;
    public Position position;

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Chel)
        {
            if(((Chel) obj).id.equals(this.id) &&
                    ((Chel) obj).position.equals(this.position) &&
                    ((Chel) obj).quantity.equals(this.quantity) &&
                    ((Chel) obj).office.equals(this.office) &&
                    ((Chel) obj).name.equals(this.name) &&
                    ((Chel) obj).spot.equals(this.spot) &&
                    ((Chel) obj).status.equals(this.status))
                return true;
        }
        return false;
    }
}
enum Status
{
    Usual,
    OldBoy,
    Disabled
}