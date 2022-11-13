package StationObjects;
public class Position {
    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public Integer x;
    public Integer y;

    @Override
    public String toString(){
        return "("+x+", "+y+")";
    }
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Position)
        {
            if(((Position)obj).y.equals(this.y) &&
                    ((Position)obj).x.equals(this.x))
                return true;
        }
        return false;
    }
}
