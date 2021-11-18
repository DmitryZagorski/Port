package com.epam.port.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    void should_put_container_to_ship() {
        //given
        Port port = new Port(1, 5);
        Ship ship = new Ship("1", 5);
        ship.setCurrentPort(port);

        ship.generateLoadedContainers();
        int currentSize = ship.getQuantityContainers();
        //when
        ship.putContainer(new Container());
        int actual = ship.getQuantityContainers();
        //then
        Assertions.assertEquals(currentSize+1, actual);
    }

    @Test
    void should_unload_one_containers_from_ship() throws InterruptedException {
        //given
        Port port = new Port(1, 5);
        Ship ship = new Ship("1", 5);
        ship.setCurrentPort(port);
        ship.generateLoadedContainers();
        int currentSize = ship.getQuantityContainers();
        //when
        ship.unloadContainer();
        int actual = ship.getQuantityContainers();
        //then
        Assertions.assertEquals(currentSize-1, actual);
    }

    @Test
    void should_unload_all_containers_from_ship() throws InterruptedException {
        //given
        Port port = new Port(1, 5);
        Ship ship = new Ship("1", 5);
        ship.setCurrentPort(port);
        ship.generateLoadedContainers();
        //when
        ship.unloadAllContainersInPort();
        int actual = ship.getQuantityContainers();
        //then
        Assertions.assertEquals(0, actual);
    }

    @Test
    void should_load_random_containers_to_ship() {
        //given
        Port port = new Port(1, 5);
        Ship ship = new Ship("1", 5);
        ship.setCurrentPort(port);
        int firstSize = ship.getQuantityContainers();
        //when
        ship.generateLoadedContainers();
        int actualSize = ship.getQuantityContainers();
        //then
        Assertions.assertTrue(firstSize<actualSize);
    }
}