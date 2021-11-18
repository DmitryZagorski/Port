
package com.epam.port.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class PortTest {

    BlockingQueue<Container> containers = new LinkedBlockingQueue<>();

    @Test
    void should_unload_all_containers_from_ship() throws InterruptedException {
        //given
        Port port = new Port(1, 5);
        Ship ship = new Ship("1", 5);
        ship.setCurrentPort(port);
        ship.generateLoadedContainers();
        //when
        ship.unloadAllContainersInPort();
        //then
        Assertions.assertEquals(0, ship.getQuantityContainers());
    }
}