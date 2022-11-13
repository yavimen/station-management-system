package com.stationmanagement.springboot;

import MapObjects.MapConfig;
import StationSimulation.IStationSimulator;
import StationSimulation.StationSimulator;
import Views.MapView;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/stationmanager")
public class StationManagerController
{
    private IStationSimulator stationSimulator;
    @PostMapping
    public MapConfig ConfigureMap(@RequestBody MapConfig config)
    {
        stationSimulator = new StationSimulator(config);
        stationSimulator.StartSimulation();
        return config;
    }

    @GetMapping
    public MapView GetCurrentMap()
    {
        return stationSimulator.GetMapView();
    }

    @PostMapping(path = "turnOffStationSimulator")
    public void TurnOffStationSimulator()
    {
        stationSimulator.DisposeAllThreads();
        stationSimulator = null;
    }
}