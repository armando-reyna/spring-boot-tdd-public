package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.datatransferobject.ManufacturerDTO;
import com.mytaxi.datatransferobject.ModelDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainobject.ModelDO;
import com.mytaxi.domainvalue.GeoCoordinate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CarMapper {

    public static ManufacturerDO getManufacturerDO(ManufacturerDTO manufacturerDTO) {
        return new ManufacturerDO(manufacturerDTO.getId());
    }

    public static ModelDO getModelDO(ModelDTO modelDTO) {
        return new ModelDO(modelDTO.getId(), getManufacturerDO(modelDTO.getManufacturer()));
    }

    public static CarDO makeCarDO(CarDTO carDTO) {
        return new CarDO(carDTO.getSerial(), carDTO.getLicensePlate(), getModelDO(carDTO.getModel()), carDTO.getYear(),
                carDTO.getColor(), carDTO.getSeatCount(), carDTO.getEngineType(), carDTO.getCarType());
    }

    public static ManufacturerDTO makeManufacturerDTO(ManufacturerDO manufacturerDO) {
        ManufacturerDTO.ManufacturerDTOBuilder carDTOBuilder = ManufacturerDTO.newBuilder()
                .setId(manufacturerDO.getId())
                .setName(manufacturerDO.getName());
        return carDTOBuilder.createManufacturerDTO();
    }

    public static ModelDTO makeModelDTO(ModelDO modelDO) {
        ModelDTO.ModelDTOBuilder carDTOBuilder = ModelDTO.newBuilder()
                .setId(modelDO.getId())
                .setName(modelDO.getName())
                .setManufacturer(makeManufacturerDTO(modelDO.getManufacturer()));
        return carDTOBuilder.createModelDTO();
    }

    public static CarDTO makeCarDTO(CarDO carDO) {
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
                .setId(carDO.getId())
                .setSerial(carDO.getSerial())
                .setLicensePlate(carDO.getLicensePlate())
                .setModel(makeModelDTO(carDO.getModel()))
                .setYear(carDO.getYear())
                .setColor(carDO.getColor())
                .setSeatCount(carDO.getSeatCount())
                .setEngineType(carDO.getEngineType())
                .setCarType(carDO.getCarType());

        Double raiting = carDO.getRaiting();
        if (raiting != null) {
            carDTOBuilder.setRaiting(raiting);
        }

        return carDTOBuilder.createCarDTO();
    }


    public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars) {
        return cars.stream()
                .map(CarMapper::makeCarDTO)
                .collect(Collectors.toList());
    }
}
