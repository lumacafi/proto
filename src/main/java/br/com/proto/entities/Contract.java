package br.com.proto.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Contract {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false)
    private String id;


    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Client client;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Service service;

    private String startDate;
    private String endDate;

    private double value;

    public String getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
