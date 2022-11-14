package GenerationCore;

import MoveManaging.IMoveManager;
import StationObjects.Chel;
import StationObjects.Map;

public class ChelGenerator extends Thread {
    protected Map map;
    protected IMoveManager manager;
    protected IChelProducer producer;
    Boolean eti = false;
    public ChelGenerator(boolean isEqualTimeInterval, Map map, IChelProducer producer, IMoveManager manager){
        eti = isEqualTimeInterval;
        this.map = map;
        this.producer = producer;
        this.manager = manager;
    }

    @Override
    public void run() {
        var maxChelLimit = (map.mapSize - 2) / 2 * map.getOffices().size();
        boolean isMaxChelLimit = false;
        while(true){
            if(!isMaxChelLimit) {
                if (maxChelLimit <= map.getPeople().size()) {
                    isMaxChelLimit = true;
                }
                else {
                    Chel newChel = producer.GetChel();
                    manager.putChelInQueue(newChel.office ,newChel);
                }
            }
            else{
                if(map.getPeople().size() <= maxChelLimit * 0.7)
                    isMaxChelLimit = false;
            }
            try{
            Thread.sleep(1000);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
