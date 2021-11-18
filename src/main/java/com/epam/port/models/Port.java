package com.epam.port.models;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Port {

    private Semaphore dock;
    private int warehouseCapacity;
    private Warehouse warehouse;

    public Port(int quantityDocks, int warehouseCapacity) {

        this.setWarehouseCapacity(warehouseCapacity);
        dock = new Semaphore(quantityDocks, true);
        warehouse = new Warehouse(warehouseCapacity);
    }

    public Semaphore getDock() {
        return dock;
    }

    public void unloadAllContainersFromShip(BlockingQueue<Container> containers) {
        //containers.forEach(container -> warehouse.putContainer(container));
        warehouse.putContainers(containers);
    }

    public Integer getQuantityContainers(){
        return warehouse.getQuantityContainers();
    }

    public void setWarehouseCapacity(int warehouseCapacity) {
        this.warehouseCapacity = warehouseCapacity;
    }

    public Warehouse getWarehouse(){
        return warehouse;
    }

}