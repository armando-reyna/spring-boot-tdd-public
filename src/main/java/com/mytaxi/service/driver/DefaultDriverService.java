package com.mytaxi.service.driver;

import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;
import com.mytaxi.util.SearchCriteria;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    private final CarService carService;

    @PersistenceContext
    private EntityManager entityManager;

    public DefaultDriverService(final DriverRepository driverRepository, final CarService carService) {
        this.driverRepository = driverRepository;
        this.carService = carService;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException {
        DriverDO driver;
        try {
            driver = driverRepository.save(driverDO);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("Some constraints are thrown due to driver creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus) {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }

    /**
     * Driver selects car
     *
     * @param driverId
     * @param carId
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void selectCar(long driverId, long carId) throws EntityNotFoundException, CarAlreadyInUseException {
        DriverDO driverDO = findDriverChecked(driverId);
        CarDO carDO = carService.find(carId);
        if (!carDO.getAvailable()) {
            throw new CarAlreadyInUseException("Car already in use");
        } else {
            carDO.setAvailable(Boolean.FALSE);
            driverDO.setCar(carDO);
        }
    }

    /**
     * Driver unselects car
     *
     * @param driverId
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void deselectCar(long driverId) throws EntityNotFoundException {
        DriverDO driverDO = findDriverChecked(driverId);
        long carId = driverDO.getCar().getId();
        driverDO.setCar(null);
        CarDO carDO = carService.find(carId);
        carDO.setAvailable(Boolean.TRUE);
    }

    /**
     * Driver unselects car
     *
     * @param params
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public List<DriverDO> searchDriver(final List<SearchCriteria> params) {

        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<DriverDO> query = builder.createQuery(DriverDO.class);
        final Root r = query.from(DriverDO.class);

        Predicate predicate = builder.conjunction();

        for (final SearchCriteria param : params) {
            if (param.getOperation().equalsIgnoreCase(">")) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
            } else if (param.getOperation().equalsIgnoreCase("<")) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
            } else if (param.getOperation().equalsIgnoreCase(":")) {
                if (param.getKey().startsWith("car_")) {
                    String key = param.getKey().substring(4);
                    LOG.info("car: key:" + key + " value:" + param.getValue());
                    Join<DriverDO, CarDO> groupJoin = r.join("car");
                    if (groupJoin.get(key).getJavaType() == String.class) {
                        predicate = builder.and(predicate, builder.like(groupJoin.get(key), "%" + param.getValue() + "%"));
                    } else {
                        predicate = builder.and(predicate, builder.equal(groupJoin.get(key), param.getValue()));
                    }
                } else if (r.get(param.getKey()).getJavaType() == String.class) {
                    predicate = builder.and(predicate, builder.like(r.get(param.getKey()), "%" + param.getValue() + "%"));
                } else if (r.get(param.getKey()).getJavaType() == OnlineStatus.class) {
                    predicate = builder.and(predicate, builder.equal(r.get(param.getKey()), OnlineStatus.valueOf("" + param.getValue())));
                } else {
                    predicate = builder.and(predicate, builder.equal(r.get(param.getKey()), param.getValue()));
                }
            }
        }
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

}
