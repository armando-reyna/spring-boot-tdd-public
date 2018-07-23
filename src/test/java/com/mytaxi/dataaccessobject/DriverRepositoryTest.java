package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainobject.ModelDO;
import com.mytaxi.domainvalue.CarType;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.util.SearchOperation;
import com.mytaxi.util.SpecSearchCriteria;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.core.IsNot.not;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DriverRepositoryTest {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void searchByUsernameContains_returnDriverList() throws EntityNotFoundException {

        DriverDO driver1 = driverRepository.findById(4L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 4"));

        final DriverSpecification spec = new DriverSpecification(new SpecSearchCriteria("username", SearchOperation.CONTAINS, "04"));
        final List<DriverDO> results = driverRepository.findAll(Specifications
                .where(spec));

        MatcherAssert.assertThat(results, hasSize(1));
        MatcherAssert.assertThat(driver1, isIn(results));
    }

    @Test
    public void searchByUsernameEqual_returnDriverList() throws EntityNotFoundException {

        DriverDO driver1 = driverRepository.findById(4L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 4"));

        final DriverSpecification spec = new DriverSpecification(new SpecSearchCriteria("username", SearchOperation.EQUALITY, "driver04"));
        final List<DriverDO> results = driverRepository.findAll(Specifications
                .where(spec));

        MatcherAssert.assertThat(results, hasSize(1));
        MatcherAssert.assertThat(driver1, isIn(results));
    }

    @Test
    public void searchByUsernameAndOnlineStatus_returnDriverList() throws EntityNotFoundException {

        DriverDO driver1 = driverRepository.findById(4L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 4"));

        final DriverSpecification spec = new DriverSpecification(new SpecSearchCriteria("username", SearchOperation.CONTAINS, "driver04"));
        final DriverSpecification spec1 = new DriverSpecification(new SpecSearchCriteria("onlineStatus", SearchOperation.EQUALITY, OnlineStatus.ONLINE));
        final List<DriverDO> results = driverRepository.findAll(Specifications
                .where(spec)
                .and(spec1));

        MatcherAssert.assertThat(results, hasSize(1));
        MatcherAssert.assertThat(driver1, isIn(results));
    }

    @Test
    public void searchByLicensePlate_returnDriverList() throws EntityNotFoundException {

        DriverDO driver1 = driverRepository.findById(4L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 4"));

        CarDO carDO1 = carRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 1"));

        driver1.setCar(carDO1);
        driverRepository.save(driver1);

        driver1 = driverRepository.findById(4L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 4"));

        final DriverSpecification spec = new DriverSpecification(new SpecSearchCriteria("licensePlate", SearchOperation.CAR_CONTAINS, "AJC"));
        final List<DriverDO> results = driverRepository.findAll(Specifications
                .where(spec));

        MatcherAssert.assertThat(results, hasSize(1));
        MatcherAssert.assertThat(driver1, isIn(results));
    }

    @Test
    public void searchByLicensePlate_return2DriverList() throws EntityNotFoundException {

        DriverDO driver1 = driverRepository.findById(4L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 4"));

        CarDO carDO1 = carRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 1"));

        driver1.setCar(carDO1);
        driverRepository.save(driver1);

        DriverDO driver2 = driverRepository.findById(5L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 4"));

        CarDO carDO2 = carRepository.findById(2L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 1"));

        driver2.setCar(carDO2);
        driverRepository.save(driver2);

        driver1 = driverRepository.findById(4L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 4"));

        driver2 = driverRepository.findById(5L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 4"));

        final DriverSpecification spec = new DriverSpecification(new SpecSearchCriteria("licensePlate", SearchOperation.CAR_CONTAINS, "J13"));
        final List<DriverDO> results = driverRepository.findAll(Specifications
                .where(spec));

        MatcherAssert.assertThat(results, hasSize(2));
        MatcherAssert.assertThat(driver1, isIn(results));
        MatcherAssert.assertThat(driver2, isIn(results));
    }

    @Test
    public void searchByColor_returnDriverList() throws EntityNotFoundException {

        DriverDO driver1 = driverRepository.findById(4L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 4"));

        CarDO carDO1 = carRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 1"));

        driver1.setCar(carDO1);
        driverRepository.save(driver1);

        driver1 = driverRepository.findById(4L)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: 4"));

        final DriverSpecification spec = new DriverSpecification(new SpecSearchCriteria("color", SearchOperation.CAR_CONTAINS, "Black"));
        final List<DriverDO> results = driverRepository.findAll(Specifications
                .where(spec));

        MatcherAssert.assertThat(results, hasSize(1));
        MatcherAssert.assertThat(driver1, isIn(results));
    }

}
