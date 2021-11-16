package com.epam.port.models;

import java.util.Date;

public class Document {

    // private boolean loadToDock = true;
    private Date timeExpiration;
    private Integer documentNumber;

    public Date getTimeExpiration() {
        return timeExpiration;
    }

    public Date setDateOfExpiration(Date date) {
        return timeExpiration;
    }

    public void setDockNumber(Integer dockNumber) {
        this.documentNumber = dockNumber;
    }

    public Integer getDocumentNumber() {
        return documentNumber;
    }
}
