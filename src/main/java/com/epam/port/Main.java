package com.epam.port;

import com.epam.port.models.Port;
import com.epam.port.models.Ship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Port port = new Port(2, 5);
        List<Ship> ships = new ArrayList<>();
        LOGGER.info("Creating ships");
        for (int i = 0; i < 5; i++) {
            Ship ship = new Ship("Ship " + (i+1), 5);
            ship.generateLoadedContainers();
            ships.add(ship);
        }
        for (Ship ship : ships) {
            ship.sendToSea(port);
        }
        LOGGER.info("Port has "+ port.getQuantityContainers() + " container(s)");
    }
}
