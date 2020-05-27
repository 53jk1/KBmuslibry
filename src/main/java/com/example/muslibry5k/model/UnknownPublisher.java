package com.example.muslibry5k.model;

import javax.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class UnknownPublisher {

    private final String name;
    private String nip;
    private String address;

    public UnknownPublisher(String name) {
        this.name = name;
    }

    public UnknownPublisher(String name, String nip, String address) {
        this.name = "Unknown";
        this.nip = "Unknown";
        this.address = "Unknown";
    }


}
