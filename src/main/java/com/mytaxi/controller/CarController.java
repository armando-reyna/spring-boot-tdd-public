package com.mytaxi.controller;

import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * All operations with a car will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(final CarService carService) {
        this.carService = carService;
    }

    @ApiOperation(value = "Get all Cars")
    @GetMapping
    public List<CarDTO> findCars() {
        return CarMapper.makeCarDTOList(carService.findAll());
    }

    @ApiOperation(value = "Get Car by Id")
    @GetMapping("/{carId}")
    public CarDTO getCar(@Valid @PathVariable long carId) throws EntityNotFoundException {
        return CarMapper.makeCarDTO(carService.find(carId));
    }

    @ApiOperation(value = "Create Car")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws ConstraintsViolationException {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        CarDO carDO2 = carService.create(carDO);
        return CarMapper.makeCarDTO(carDO2);
    }

    @ApiOperation(value = "Update Car")
    @PutMapping("/{carId}")
    public CarDTO update(@Valid @PathVariable long carId, @Valid @RequestBody CarDTO carDTO)
            throws ConstraintsViolationException, EntityNotFoundException {
        CarDO carDO = CarMapper.makeCarDO(carDTO);
        return CarMapper.makeCarDTO(carService.update(carId, carDO));
    }

    @ApiOperation(value = "Delete Car")
    @DeleteMapping("/{carId}")
    public void deleteCar(@Valid @PathVariable long carId) throws EntityNotFoundException {
        carService.delete(carId);
    }

}
