package com.mytaxi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainobject.ModelDO;
import com.mytaxi.domainvalue.CarType;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.service.car.DefaultCarService;
import com.mytaxi.service.driver.DefaultDriverService;
import com.mytaxi.util.SearchCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefaultDriverService driverService;

    @MockBean
    private DefaultCarService carService;

    @Test
    public void selectCar_ReturnsNull() throws Exception {

        ModelDO model = new ModelDO(1L, "Jetta", new ManufacturerDO(1L, "Volkswagen"));
        CarDO savedCar = new CarDO("123456789ABCDEFGH", "J13AJC", model, 2016, "Black", 5, EngineType.GAS, CarType.SEDAN);
        savedCar.setId(1L);

        DriverDO driverDO = new DriverDO("driver04pw", "driver04");

        when(this.driverService.find( 4L)).thenReturn(driverDO);
        when(this.carService.find( 1L)).thenReturn(savedCar);

        this.mockMvc.perform(put("/v1/drivers/car/select/{driverId}/{carId}", 4L, 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void selectCar_Returns409() throws Exception {
        doThrow(new CarAlreadyInUseException("")).when(this.driverService).selectCar(4L, 1L);

        this.mockMvc.perform(put("/v1/drivers/car/select/{driverId}/{carId}", 4L, 1L))
                .andExpect(status().isConflict());
    }


    @Test
    public void unselectCar_ReturnsNull() throws Exception {

        ModelDO model = new ModelDO(1L, "Jetta", new ManufacturerDO(1L, "Volkswagen"));
        CarDO savedCar = new CarDO("123456789ABCDEFGH", "J13AJC", model, 2016, "Black", 5, EngineType.GAS, CarType.SEDAN);
        savedCar.setId(1L);

        DriverDO driverDO = new DriverDO("driver04pw", "driver04");

        when(this.driverService.find( 4L)).thenReturn(driverDO);
        when(this.carService.find( 1L)).thenReturn(savedCar);

        this.mockMvc.perform(put("/v1/drivers/car/select/{driverId}/{carId}", 4L, 1L))
                .andExpect(status().isOk());

        this.mockMvc.perform(put("/v1/drivers/car/deselect/{driverId}", 4L))
                .andExpect(status().isOk());
    }


    @Test
    public void search_ReturnsOneDriver() throws Exception {

        ModelDO model = new ModelDO(1L, "Jetta", new ManufacturerDO(1L, "Volkswagen"));
        CarDO savedCar = new CarDO("123456789ABCDEFGH", "J13AJC", model, 2016, "Black", 5, EngineType.GAS, CarType.SEDAN);
        savedCar.setId(1L);

        DriverDO driverDO = new DriverDO("driver04pw", "driver04");
        driverDO.setCar(savedCar);

        List<DriverDO> drivers = new ArrayList<>();
        drivers.add(driverDO);

        when(this.driverService.searchDriver(any())).thenReturn(drivers);

        this.mockMvc.perform(get("/v1/drivers/search").param("search","username:driver04"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void searchByCar_ReturnsOneDriver() throws Exception {

        ModelDO model = new ModelDO(1L, "Jetta", new ManufacturerDO(1L, "Volkswagen"));
        CarDO savedCar = new CarDO("123456789ABCDEFGH", "J13AJC", model, 2016, "Black", 5, EngineType.GAS, CarType.SEDAN);
        savedCar.setId(1L);

        DriverDO driverDO = new DriverDO("driver04pw", "driver04");
        driverDO.setCar(savedCar);

        List<DriverDO> drivers = new ArrayList<>();
        drivers.add(driverDO);

        when(this.driverService.searchDriver(any())).thenReturn(drivers);

        this.mockMvc.perform(get("/v1/drivers/search").param("search","car_licensePlate:J13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

}
