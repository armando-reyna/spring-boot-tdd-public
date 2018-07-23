package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mytaxi.domainvalue.CarType;
import com.mytaxi.domainvalue.EngineType;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {

    @JsonIgnore
    private Long id;

    @NotNull(message = "Serial can not be null!")
    private String serial;

    @NotNull(message = "License plate can not be null!")
    private String licensePlate;

    @NotNull(message = "Model can not be null!")
    private ModelDTO model;

    @NotNull(message = "Year can not be null!")
    private Integer year;

    @NotNull(message = "Color can not be null!")
    private String color;

    @NotNull(message = "Seat Count can not be null!")
    private Integer seatCount;

    @NotNull(message = "Engine Type can not be null!")
    private EngineType engineType;

    @NotNull(message = "Car Type can not be null!")
    private CarType carType;

    private Double raiting;

    private CarDTO() {
    }

    private CarDTO(Long id, String serial, String licensePlate, ModelDTO model, Integer year, String color,
                   Integer seatCount, EngineType engineType, CarType carType, Double raiting) {
        this.id = id;
        this.serial = serial;
        this.licensePlate = licensePlate;
        this.model = model;
        this.year = year;
        this.color = color;
        this.seatCount = seatCount;
        this.engineType = engineType;
        this.carType = carType;
        this.raiting = raiting;
    }

    public static CarDTOBuilder newBuilder() {
        return new CarDTOBuilder();
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    public String getSerial() {
        return serial;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public ModelDTO getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public CarType getCarType() {
        return carType;
    }

    public Double getRaiting() {
        return raiting;
    }

    public static class CarDTOBuilder {

        private Long id;
        private String serial;
        private String licensePlate;
        private ModelDTO model;
        private Integer year;
        private String color;
        private Integer seatCount;
        private EngineType engineType;
        private CarType carType;
        private Double raiting;

        public CarDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public CarDTOBuilder setSerial(String serial) {
            this.serial = serial;
            return this;
        }

        public CarDTOBuilder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public CarDTOBuilder setModel(ModelDTO model) {
            this.model = model;
            return this;
        }

        public CarDTOBuilder setYear(Integer year) {
            this.year = year;
            return this;
        }

        public CarDTOBuilder setColor(String color) {
            this.color = color;
            return this;
        }

        public CarDTOBuilder setSeatCount(Integer seatCount) {
            this.seatCount = seatCount;
            return this;
        }

        public CarDTOBuilder setEngineType(EngineType engineType) {
            this.engineType = engineType;
            return this;
        }

        public CarDTOBuilder setCarType(CarType carType) {
            this.carType = carType;
            return this;
        }

        public CarDTOBuilder setRaiting(Double raiting) {
            this.raiting = raiting;
            return this;
        }

        public CarDTO createCarDTO() {
            return new CarDTO(id, serial, licensePlate, model, year, color, seatCount, engineType, carType, raiting);
        }

    }
}
