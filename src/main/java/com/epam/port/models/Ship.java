package com.epam.port.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class Ship extends Thread {

    private Semaphore currentDock;
    private Port currentPort;
    private String shipName;
    private int maxCapacity;
    private BlockingQueue<Container> containers;

    private static final Logger LOGGER = LoggerFactory.getLogger(Ship.class);

    public Ship(String shipName, int maxCapacity) {
        this.shipName = shipName;
        this.maxCapacity = maxCapacity;
        this.containers = new LinkedBlockingQueue<>();
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public void putContainer(Container container) {
//        if (currentDock == null) {
//            throw new IllegalStateException("Putting impossible! Because the ship isn't in port!");
//        }
        if (containers.size() == maxCapacity) {
            throw new IndexOutOfBoundsException("Ship full of containers");
        }
        try {
            containers.put(container);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Container unloadContainer() throws InterruptedException {
        checkingBeforeUnloadContainers();

        return containers.take();
    }

    public BlockingQueue<Container> unloadAllContainersInPort() throws InterruptedException {
        checkingBeforeUnloadContainers();

        int availablePlaces = currentPort.getWarehouse().getAvailablePlaces();
        BlockingQueue<Container> unloadingContainers;

        if (availablePlaces > containers.size()) {
            unloadingContainers = new LinkedBlockingQueue<>(containers);
            containers.clear();
            return unloadingContainers;
        }

        unloadingContainers = new LinkedBlockingQueue<>();

        for (int i = 0; i < availablePlaces; i++) {
            unloadingContainers.put(unloadContainer());
        }

        return unloadingContainers;

    }

    private void checkingBeforeUnloadContainers() {
//        if (currentDock == null) {
//            LOGGER.warn("Ship" + this.shipName + " isn't in port!");
//        }
        if (containers.isEmpty()) {
            interrupt();
            LOGGER.warn("Ship" + this.shipName + " don't have any containers");
        }
        if (currentPort.getWarehouse().getAvailablePlaces() == 0) {
            interrupt();
            LOGGER.warn(this.shipName + " can't unload containers, because warehouse is full");
        }
    }

    public void showAllContainers() {
        if (containers.isEmpty()) {
            LOGGER.info("Ship don't have any containers");
        } else {
            LOGGER.info(shipName + " has " + getQuantityContainers() + " containers");
        }
    }

    public void generateLoadedContainers() {
        LOGGER.info("Loading " + this.shipName + " by containers");
        int quantityLoadedContainers = (int) (1 + Math.random() * maxCapacity);

        for (int i = 0; i < quantityLoadedContainers; i++) {
            containers.add(new Container());
        }

        LOGGER.info(shipName + " loaded by " + quantityLoadedContainers + " container(s)");
    }

    public Integer getQuantityContainers() {
        return containers.size();
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void sendToSea(Port port) {
        setCurrentPort(port);
        start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
            LOGGER.info(shipName + " sailed to Port and waiting for dock");
            currentDock = currentPort.getDock();
            currentDock.acquire();
            LOGGER.info(shipName + " got access to pier of dock");
            currentPort.unloadAllContainersFromShip(unloadAllContainersInPort());
            if (this.containers.size() == 0) {
                LOGGER.info(shipName + " unloaded containers and release dock");
            } else {
                LOGGER.info(shipName + " will be waiting for unloading in sea");
            }
            currentDock.release();
        } catch (InterruptedException e) {
            interrupt();
            LOGGER.warn("Ship can't sail into port");
            //e.printStackTrace();
        }
    }

}