package StationSimulation;

import Views.MapView;

public interface IStationSimulator {
    MapView getMapView();
    void startSimulation();
    void disposeAllThreads();
}
