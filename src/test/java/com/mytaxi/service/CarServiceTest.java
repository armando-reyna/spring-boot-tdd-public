package com.mytaxi.service;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainobject.ModelDO;
import com.mytaxi.domainvalue.CarType;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.DefaultCarService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    private DefaultCarService carService;

    @Before
    public void setUp() {
        carService = new DefaultCarService(carRepository);
    }

    @Test
    public void createCar_returnsCarInfo() throws ConstraintsViolationException {
        ModelDO model = new ModelDO(1L, "Jetta", new ManufacturerDO(1L, "Volkswagen"));
        CarDO car = new CarDO("123456789ABCDEFGH", "J13AJC", model, 2016, "Black", 5, EngineType.GAS, CarType.SEDAN);

        CarDO savedCar = new CarDO("123456789ABCDEFGH", "J13AJC", model, 2016, "Black", 5, EngineType.GAS, CarType.SEDAN);
        savedCar.setId(1L);

        given(carRepository.save(car)).willReturn(savedCar);
        CarDO newCar = carService.create(car);
        assertThat(newCar.getId()).isEqualTo(1L);
        assertThat(newCar.getSerial()).isEqualTo("123456789ABCDEFGH");
        assertThat(newCar.getLicensePlate()).isEqualTo("J13AJC");
        assertThat(newCar.getModel()).isEqualTo(model);
        assertThat(newCar.getYear()).isEqualTo(2016);
        assertThat(newCar.getColor()).isEqualTo("Black");
        assertThat(newCar.getSeatCount()).isEqualTo(5);
        assertThat(newCar.getEngineType()).isEqualTo(EngineType.GAS);
        assertThat(newCar.getCarType()).isEqualTo(CarType.SEDAN);
    }

    @Test
    public void getCar_returnsCarInfo() throws EntityNotFoundException {
        ModelDO model = new ModelDO(1L, "Jetta", new ManufacturerDO(1L, "Volkswagen"));

        CarDO savedCar = new CarDO("123456789ABCDEFGH", "J13AJC", model, 2016, "Black", 5, EngineType.GAS, CarType.SEDAN);
        savedCar.setId(1L);

        given(carRepository.findById(1L)).willReturn(java.util.Optional.ofNullable(savedCar));
        CarDO newCar = carService.find(1L);

        assertThat(newCar.getId()).isEqualTo(1L);
        assertThat(newCar.getSerial()).isEqualTo("123456789ABCDEFGH");
        assertThat(newCar.getLicensePlate()).isEqualTo("J13AJC");
        assertThat(newCar.getModel()).isEqualTo(model);
        assertThat(newCar.getYear()).isEqualTo(2016);
        assertThat(newCar.getColor()).isEqualTo("Black");
        assertThat(newCar.getSeatCount()).isEqualTo(5);
        assertThat(newCar.getEngineType()).isEqualTo(EngineType.GAS);
        assertThat(newCar.getCarType()).isEqualTo(CarType.SEDAN);
    }

    //todo: createCar_returnsCarInfo

    //todo: createCar_returnsCarInfo

    //todo: deleteCar_returnsVoid

}
