package com.mytaxi.domainobject;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(name = "model")
public class ModelDO {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    @NotNull(message = "Name can not be null!")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "manufacturer_id")
    @NotNull(message = "Manufacturer can not be null!")
    private ManufacturerDO manufacturer;

    @Column(nullable = false)
    private Boolean deleted = false;

    private ModelDO() {
    }

    public ModelDO(Long id, ManufacturerDO manufacturer) {
        this.id = id;
        this.manufacturer = manufacturer;
    }

    public ModelDO(Long id, String name, ManufacturerDO manufacturer) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ManufacturerDO getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerDO manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
