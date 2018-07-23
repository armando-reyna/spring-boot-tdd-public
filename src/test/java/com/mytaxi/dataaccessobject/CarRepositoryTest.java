package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainobject.ModelDO;
import com.mytaxi.domainvalue.CarType;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.exception.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createCar_ReturnsCar() {

        ModelDO model = new ModelDO(1L, "Jetta", new ManufacturerDO(1L, "Volkswagen"));
        CarDO car = this.carRepository.save(new CarDO("123456789ABCDEFGK", "J13AJC", model, 2016, "Black", 5, EngineType.GAS, CarType.SEDAN));

        assertThat(car.getId()).isEqualTo(4L);
        assertThat(car.getSerial()).isEqualTo("123456789ABCDEFGK");
        assertThat(car.getLicensePlate()).isEqualTo("J13AJC");
        assertThat(car.getModel()).isEqualTo(model);
        assertThat(car.getYear()).isEqualTo(2016);
        assertThat(car.getColor()).isEqualTo("Black");
        assertThat(car.getSeatCount()).isEqualTo(5);
        assertThat(car.getEngineType()).isEqualTo(EngineType.GAS);
        assertThat(car.getCarType()).isEqualTo(CarType.SEDAN);
    }

    @Test
    public void getCar_ReturnsCar() throws EntityNotFoundException {

        CarDO car = carRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 1"));

        assertThat(car.getId()).isEqualTo(1L);
        assertThat(car.getSerial()).isEqualTo("123456789ABCDEFGH");
        assertThat(car.getLicensePlate()).isEqualTo("J13AJC");
        assertThat(car.getModel().getId()).isEqualTo(1L);
        assertThat(car.getYear()).isEqualTo(2016);
        assertThat(car.getColor()).isEqualTo("Black");
        assertThat(car.getSeatCount()).isEqualTo(5);
        assertThat(car.getEngineType()).isEqualTo(EngineType.GAS);
        assertThat(car.getCarType()).isEqualTo(CarType.SEDAN);
    }

    @Test
    public void updateCar_ReturnsCar() throws EntityNotFoundException {

        CarDO car = carRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 1"));
        car.setSerial("123456789ABCDEFGL");
        car.setLicensePlate("J13AJX");
        car.setYear(2000);
        car.setColor("White");
        car = carRepository.save(car);

        assertThat(car.getId()).isEqualTo(1L);
        assertThat(car.getSerial()).isEqualTo("123456789ABCDEFGL");
        assertThat(car.getLicensePlate()).isEqualTo("J13AJX");
        assertThat(car.getYear()).isEqualTo(2000);
        assertThat(car.getColor()).isEqualTo("White");
    }

    @Test
    public void getCars_ReturnsCars() throws EntityNotFoundException {

        List<CarDO> cars = carRepository.findByDeletedFalse();

        CarDO car = cars.get(0);
        assertThat(car.getId()).isEqualTo(1L);
        assertThat(car.getSerial()).isEqualTo("123456789ABCDEFGH");
        assertThat(car.getLicensePlate()).isEqualTo("J13AJC");
        assertThat(car.getModel().getId()).isEqualTo(1L);
        assertThat(car.getYear()).isEqualTo(2016);
        assertThat(car.getColor()).isEqualTo("Black");
        assertThat(car.getSeatCount()).isEqualTo(5);
        assertThat(car.getEngineType()).isEqualTo(EngineType.GAS);
        assertThat(car.getCarType()).isEqualTo(CarType.SEDAN);

        car = cars.get(1);
        assertThat(car.getId()).isEqualTo(2L);
        assertThat(car.getSerial()).isEqualTo("123456789ABCDEFGI");
        assertThat(car.getLicensePlate()).isEqualTo("J13AJD");
        assertThat(car.getModel().getId()).isEqualTo(2L);
        assertThat(car.getYear()).isEqualTo(2017);
        assertThat(car.getColor()).isEqualTo("White");
        assertThat(car.getSeatCount()).isEqualTo(5);
        assertThat(car.getEngineType()).isEqualTo(EngineType.HYBRID);
        assertThat(car.getCarType()).isEqualTo(CarType.SEDAN);

        car = cars.get(2);
        assertThat(car.getId()).isEqualTo(3L);
        assertThat(car.getSerial()).isEqualTo("123456789ABCDEFGJ");
        assertThat(car.getLicensePlate()).isEqualTo("J13AJE");
        assertThat(car.getModel().getId()).isEqualTo(3L);
        assertThat(car.getYear()).isEqualTo(2018);
        assertThat(car.getColor()).isEqualTo("White");
        assertThat(car.getSeatCount()).isEqualTo(5);
        assertThat(car.getEngineType()).isEqualTo(EngineType.ELECTRIC);
        assertThat(car.getCarType()).isEqualTo(CarType.HATCHBACK);
    }

    @Test
    public void deleteCar_ReturnsCar() throws EntityNotFoundException {

        CarDO car = carRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 1"));
        car.setDeleted(Boolean.TRUE);
        car = carRepository.save(car);

        assertThat(car.getId()).isEqualTo(1L);
        assertThat(car.getDeleted()).isEqualTo(Boolean.TRUE);

    }

}
