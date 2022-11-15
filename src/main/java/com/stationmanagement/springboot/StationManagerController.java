package com.stationmanagement.springboot;

import MapObjects.MapConfig;
import StationSimulation.IStationSimulator;
import StationSimulation.StationSimulator;
import Views.MapView;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/stationmanager")
public class StationManagerController
{
    private IStationSimulator stationSimulator;
    @PostMapping
    public MapConfig ConfigureMap(@RequestBody MapConfig config)
    {
        stationSimulator = new StationSimulator(config);
        stationSimulator.startSimulation();
        return config;
    }

    @GetMapping
    public MapView GetCurrentMap()
    {
        return stationSimulator.getMapView();
    }

    @PostMapping(path = "turnOffStationSimulator")
    public void TurnOffStationSimulator()
    {
        stationSimulator.disposeAllThreads();
        stationSimulator = null;
    }
}