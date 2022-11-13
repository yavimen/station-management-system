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
        map = MapConfigurator.GenerateTestMap1();
        moveManager = MapConfigurator.getMoveManager();
        IChelProducer producer = new ChelProducer(map);
        generator = new ChelGenerator(true, map, producer, moveManager);
    }
    public StationSimulator(MapConfig config){
        map = MapConfigurator.CreateMap(config);
        moveManager = MapConfigurator.getMoveManager();
        IChelProducer producer = new ChelProducer(map);
        generator = new ChelGenerator(true, map, producer, moveManager);
    }

     public MapView GetMapView() {
         return new MapView(map);
     }
    public void StartSimulation(){
        map.StartAllOffices();
        generator.start();
    }

    public void DisposeAllThreads(){
        generator.interrupt();
        map.getOffices().forEach(o->o.interrupt());
    }
}
