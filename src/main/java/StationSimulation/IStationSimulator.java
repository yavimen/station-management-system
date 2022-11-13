package StationSimulation;

import Views.MapView;

public interface IStationSimulator {
    MapView GetMapView();
    void StartSimulation();
    void DisposeAllThreads();
}
