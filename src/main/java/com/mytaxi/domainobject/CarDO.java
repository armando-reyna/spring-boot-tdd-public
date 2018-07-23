package com.mytaxi.domainobject;

import com.mytaxi.domainvalue.CarType;
import com.mytaxi.domainvalue.EngineType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(
        name = "car",
        uniqueConstraints = @UniqueConstraint(name = "uc_serial", columnNames = {"serial"})
)
public class CarDO {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    @NotNull(message = "Serial can not be null!")
    private String serial;

    @Column(nullable = false)
    @NotNull(message = "License plate can not be null!")
    private String licensePlate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "model_id")
    @NotNull(message = "Model can not be null!")
    private ModelDO model;

    @Column(nullable = false)
    @NotNull(message = "Year can not be null!")
    private Integer year;

    @Column(nullable = false)
    @NotNull(message = "Color can not be null!")
    private String color;

    @Column(nullable = false)
    @NotNull(message = "Seat Count can not be null!")
    private Integer seatCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Engine Type can not be null!")
    private EngineType engineType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Car Type can not be null!")
    private CarType carType;

    @Column
    private Double raiting;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column(nullable = false)
    private Boolean available = true;

    private CarDO() {
    }

    public CarDO(String serial, String licensePlate, ModelDO model, Integer year, String color, Integer seatCount, EngineType engineType, CarType carType) {
        this.serial = serial;
        this.licensePlate = licensePlate;
        this.model = model;
        this.year = year;
        this.color = color;
        this.seatCount = seatCount;
        this.engineType = engineType;
        this.carType = carType;
        this.raiting = null;
        this.deleted = false;
        this.available = false;
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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public ModelDO getModel() {
        return model;
    }

    public void setModel(ModelDO model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public Double getRaiting() {
        return raiting;
    }

    public void setRaiting(Double raiting) {
        this.raiting = raiting;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
