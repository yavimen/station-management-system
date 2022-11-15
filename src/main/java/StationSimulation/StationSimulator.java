package StationSimulation;

import GenerationCore.ChelGenerator;
import GenerationCore.ChelProducer;
import GenerationCore.IChelProducer;
import MapObjects.MapConfig;
import MapObjects.MapConfigurator;
import MoveManaging.*;
import StationObjects.*;
import Views.MapView;


public class StationSimulator implements IStationSimulator {
    protected IMoveManager moveManager;
    protected Map map;
    protected ChelGenerator generator;
    public StationSimulator(){
        map = MapConfigurator.generateTestMap2();
        moveManager = MapConfigurator.getMoveManager();
        IChelProducer producer = new ChelProducer(map);
        generator = new ChelGenerator(true, map, producer, moveManager);
    }
    public StationSimulator(MapConfig config){
        map = MapConfigurator.createMap(config);
        moveManager = MapConfigurator.getMoveManager();
        IChelProducer producer = new ChelProducer(map);
        generator = new ChelGenerator(config.isRandomSpawnTime, map, producer, moveManager);
    }

    public MapView getMapView() {
         return new MapView(map);
     }

    public void startSimulation(){
        map.getOffices().forEach(o->o.start());
        map.reserveTicketOffice.start();
        generator.start();
    }

    public void disposeAllThreads(){
        generator.interrupt();
        map.reserveTicketOffice.interrupt();
        map.getOffices().forEach(o->o.interrupt());
    }
}
