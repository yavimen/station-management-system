package Views;

import StationObjects.*;

public class ChelView {
    public Integer id;
    public Position initialPosition;
    public Position targetPosition;
    public ChelStatus status;
    public  ChelView(Chel chel){
        id = chel.id;
        initialPosition = chel.position;
        targetPosition = chel.targetPosition;
        status = chel.chelStatus;
    }
}
