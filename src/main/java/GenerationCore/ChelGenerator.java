package GenerationCore;

import MoveManaging.IMoveManager;
import StationObjects.Chel;
import StationObjects.Map;

import java.util.Random;
import java.util.stream.Collectors;

public class ChelGenerator extends Thread {
    protected Map map;
    protected IMoveManager manager;
    protected IChelProducer producer;
    Boolean isRandomSpawnTime;
    public ChelGenerator(boolean isRandomSpawnTime, Map map, IChelProducer producer, IMoveManager manager){
        this.isRandomSpawnTime = isRandomSpawnTime;
        this.map = map;
        this.producer = producer;
        this.manager = manager;
    }

    @Override
    public void run() {
        boolean isMaxChelLimit = false;
        var maxChelLimit = (map.mapSize - 2) / 2 * map.getOffices().size();
        try{
            while(true){
                //визначає ліміт за кількістю активних кас, хз чи взагалі треба це
                maxChelLimit = (map.mapSize - 2) / 2 * map.getOffices().stream()
                        .filter(o->o.getIsManaging() == true).toList().size();
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
                Thread.sleep(GetSleepTime());

            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    protected Integer GetSleepTime(){
        if(!isRandomSpawnTime){
            return 1000;
        }
        Random random = new Random();

        return (random.nextInt(9) + 5) * 100;
    }
}
