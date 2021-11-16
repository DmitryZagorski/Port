package com.epam.port.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Container extends Thread {

    private Integer containerNumber;
    private Port port;
    private Document document;
    private boolean inShip = false;

    private static final Logger Log = LoggerFactory.getLogger(Container.class);

    public Container(Integer containerNumber, Port port, Document document) {
        this.containerNumber = containerNumber;
        this.port = port;
        this.document = document;
        setName("Container No " + containerNumber);
    }

    @Override
    public void run() {
        try {
            sleep(10);

            if (document != null) {
                Dock dock = port.getDockByNumber(document.getDocumentNumber());
                sleep(300);
                dock.addForWait(this);

                Log.info("Container No {} is in dock No {}", containerNumber, dock.getDockNumber());

                if (!inShip) {
                    dock.removeFromWait(this);
                    Log.info("Container No {} moved to customer", this.containerNumber);
                }

            } else {





                Log.info("No documents for container No {}", this.containerNumber);
            }
        } catch (InterruptedException e) {
            interrupt();
            Log.error("Container disappeared", e);
            throw new RuntimeException();
        }
    }

    public void inShip() {
        inShip = true;
    }

    public Integer getNumber() {
        return containerNumber;
    }
}
