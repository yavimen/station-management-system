package com.stationmanagement.springboot;

import StationObjects.*;
import StationSimulation.StationSimulator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StationManagementSystemApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test void Map_IsFreePosition(){

        ArrayList<Spot> arrayList = new ArrayList<>();
        ArrayList<TicketOffice> arrayList2 = new ArrayList<>();

        Map map = new Map(arrayList2, arrayList);
        map.addPerson(new Chel(1, 0, 1, "Name", null, null, new Position(1,2)));
        map.addPerson(new Chel(2, 0, 2, "Name2", null, null, new Position(3,1)));
        assertEquals(true, map.IsFreePosition(new Position(1,1)));
        assertEquals(false, map.IsFreePosition(new Position(3,1)));
        assertEquals(false, map.IsFreePosition(new Position(1,2)));
    }

    @Test void SimulationTest(){

        StationSimulator stationSimulator= new StationSimulator();
        stationSimulator.StartSimulation();
        try {
            Thread.sleep(60000);
        }catch (Exception ex){

        }
        stationSimulator.DisposeAllThreads();
    }
}
