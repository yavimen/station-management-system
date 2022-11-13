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

/*    @Test
    void TestAddingChelsToQueue(){

        TicketOffice office = new TicketOffice();
        office.start();

        try {
            sleep(1000);
            synchronized (office)
            {
                if(office.getState().toString() != "RUNNING")
                {
                    office.notify();
                    CompletableFuture.runAsync(()->{
                        office.AddToQueue(new Chel(1, 1, 1, "Name1", office, null, null));
                    });
                    sleep(20);
                    CompletableFuture.runAsync(()->{
                        office.AddToQueue(new Chel(2, 1, 1, "Name2", office, null, null));
                    });
                    sleep(20);
                    CompletableFuture.runAsync(()->{
                        office.AddToQueue(new Chel(3, 1, 1, "Name3", office, null, null));
                    });
                }
            }
            sleep(2000);

            synchronized (office)
            {
                if(office.getState().toString() != "RUNNING")
                {
                    office.notify();

                    CompletableFuture.runAsync(()-> {
                        office.AddToQueue(new Chel(4, 1, 1, "Name4", office, null, null));
                    });
                    CompletableFuture.runAsync(()-> {
                        office.AddToQueue(new Chel(5, 1, 1, "Name5", office, null, null));
                    });
                }
            }

            sleep(5000);

            synchronized (office)
            {
                if(office.getState().toString() != "RUNNING")
                {
                    office.notify();

                    CompletableFuture.runAsync(()-> {
                        office.AddToQueue(new Chel(6, 1, 1, "Name6", office, null, null));
                    });
                    CompletableFuture.runAsync(()-> {
                        office.AddToQueue(new Chel(7, 1, 1, "Name7", office, null, null));
                    });

                }
            }

            sleep(50);

            synchronized (office)
            {
                if(office.getState().toString() != "RUNNING")
                {
                    office.notify();

                    CompletableFuture.runAsync(()-> {
                        office.AddToQueue(new Chel(8, 1, 1, "Name8", office, null, null));
                    });
                    CompletableFuture.runAsync(()-> {
                        office.AddToQueue(new Chel(9, 1, 1, "Name9", office, null, null));
                    });

                }
            }

            office.join();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void TestMapAddingChelsToQueue()
    {
        ArrayList<TicketOffice> allOffices = new ArrayList<>();
        allOffices.add(new TicketOffice());
        Map newMap = new Map(allOffices, null);

        try {
            sleep(1000);
            newMap.AddChelToOfficeQueue(allOffices.get(0), new Chel(1,1,1,"Name1", allOffices.get(0), null, null));
            newMap.AddChelToOfficeQueue(allOffices.get(0), new Chel(2,1,1,"Name2", allOffices.get(0), null, null));
            newMap.AddChelToOfficeQueue(allOffices.get(0), new Chel(3,1,1,"Name3", allOffices.get(0), null, null));

            sleep(2000);
            newMap.AddChelToOfficeQueue(allOffices.get(0), new Chel(4,1,1,"Name4", allOffices.get(0), null, null));
            newMap.AddChelToOfficeQueue(allOffices.get(0), new Chel(5,1,1,"Name5", allOffices.get(0), null, null));
            newMap.AddChelToOfficeQueue(allOffices.get(0), new Chel(6,1,1,"Name6", allOffices.get(0), null, null));

            sleep(5000);
            newMap.AddChelToOfficeQueue(allOffices.get(0), new Chel(7,1,1,"Name7", allOffices.get(0), null, null));
            newMap.AddChelToOfficeQueue(allOffices.get(0), new Chel(8,1,1,"Name8", allOffices.get(0), null, null));

            sleep(50);
            newMap.AddChelToOfficeQueue(allOffices.get(0), new Chel(9,1,1,"Name9", allOffices.get(0), null, null));
            newMap.AddChelToOfficeQueue(allOffices.get(0), new Chel(10,1,1,"Name10", allOffices.get(0), null, null));

            allOffices.get(0).join();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

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
