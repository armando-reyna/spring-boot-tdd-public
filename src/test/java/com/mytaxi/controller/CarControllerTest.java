package com.mytaxi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainobject.ModelDO;
import com.mytaxi.domainvalue.CarType;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.service.car.DefaultCarService;
import com.mytaxi.service.driver.DefaultDriverService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DefaultDriverService defaultDriverService;

    @MockBean
    private DefaultCarService carService;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void create_ReturnsCar() throws Exception {

        ModelDO model = new ModelDO(1L, "Jetta", new ManufacturerDO(1L, "Volkswagen"));

        CarDO car = new CarDO("123456789ABCDEFGH", "J13AJC", model, 2016, "Black", 5, EngineType.GAS, CarType.SEDAN);
        CarDTO carDTO = CarMapper.makeCarDTO(car);

        CarDO savedCar = new CarDO("123456789ABCDEFGH", "J13AJC", model, 2016, "Black", 5, EngineType.GAS, CarType.SEDAN);
        savedCar.setId(1L);

        when(this.carService.create(any())).thenReturn(savedCar);

        this.mockMvc.perform(post("/v1/cars").contentType(MediaType.APPLICATION_JSON).content(asJsonString(carDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("serial").value("123456789ABCDEFGH"))
                .andExpect(jsonPath("licensePlate").value("J13AJC"))
                .andExpect(jsonPath("model").exists())
                .andExpect(jsonPath("year").value(2016))
                .andExpect(jsonPath("color").value("Black"))
                .andExpect(jsonPath("seatCount").value(5))
                .andExpect(jsonPath("engineType").value("GAS"))
                .andExpect(jsonPath("carType").value("SEDAN"));
    }

    @Test
    public void create_MissingProps_Returns400() throws Exception {
        when(this.carService.create(any())).thenReturn(null);
        this.mockMvc.perform(post("/v1/cars"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void get_ReturnsCar() throws Exception {

        ModelDO model = new ModelDO(1L, "Jetta", new ManufacturerDO(1L, "Volkswagen"));

        CarDO savedCar = new CarDO("123456789ABCDEFGH", "J13AJC", model, 2016, "Black", 5, EngineType.GAS, CarType.SEDAN);
        savedCar.setId(1L);

        when(this.carService.find(1L)).thenReturn(savedCar);

        this.mockMvc.perform(get("/v1/cars/{carId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("serial").value("123456789ABCDEFGH"))
                .andExpect(jsonPath("licensePlate").value("J13AJC"))
                .andExpect(jsonPath("model").exists())
                .andExpect(jsonPath("year").value(2016))
                .andExpect(jsonPath("color").value("Black"))
                .andExpect(jsonPath("seatCount").value(5))
                .andExpect(jsonPath("engineType").value("GAS"))
                .andExpect(jsonPath("carType").value("SEDAN"));
    }

    //todo: getAll_ReturnsCars()

    //todo: update_ReturnsCar()

    //todo: delete_ReturnsVoid()

}
