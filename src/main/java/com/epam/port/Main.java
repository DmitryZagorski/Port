package com.epam.port;

import com.epam.port.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Integer count = 1;
    private static final Logger Log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        List<Dock> dockList = docks();

        Port port = new Port(dockList);

        List<Ship> shipList = getShips(port);

        shipList.forEach(Thread::start);

    }

    private static List<Container> containers(Port port) {
        List<Container> containers = new ArrayList<>();
        containers.add(getContainers(port));
        containers.add(getContainers(port));
        containers.add(getContainers(port));
        return containers;
    }

    private static Container getContainers(Port port) {
        Document document = null;
        if ((int) (Math.random() * 100) < 80) {
            document = new Document();
            document.setDockNumber((int) (Math.random() * 100) < 70 ? 1 : 2);
        }
        Container container = new Container(count, port, document);
        count+=1;
        return container;
    }

    private static List<Ship> getShips(Port port) {

        List<Ship> shipList = new ArrayList<>();
        shipList.add(new Ship(1, containers(port), port));
        shipList.add(new Ship(2, containers(port), port));
        shipList.add(new Ship(3, containers(port), port));
        shipList.add(new Ship(4, containers(port), port));
        shipList.add(new Ship(5, containers(port), port));
        return shipList;
    }

    private static List<Dock> docks() {
        List<Dock> dockList = new ArrayList<>();
        dockList.add(new Dock(1));
        dockList.add(new Dock(2));
        return dockList;
    }
}
