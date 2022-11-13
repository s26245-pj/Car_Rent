package pl.pjatk.car_rent.service;

import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.stereotype.Service;
import pl.pjatk.car_rent.exceptions.DataBaseException;
import pl.pjatk.car_rent.exceptions.ValidationException;
import pl.pjatk.car_rent.model.Car;
import pl.pjatk.car_rent.repository.CarRepository;

import java.util.Optional;

@Service
public class RentalService {
    private final CarRepository carRepository;

    public RentalService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void addNewCar(Car car) {
        if (isValid(car.getVin())){
            throw new ValidationException("Vin is required");
        }
        if (isValid(car.getMake())){
            throw new ValidationException("Make is required");
        }

        try {
            carRepository.addCar(car);
        } catch (Exception e) {
            throw new DataBaseException("Something went wrong",e);
        }
    }

    public Optional<Car> findCarById(int id){
        return carRepository.findCarById(id);
    }

    private static boolean isValid(String carAttribute) {
        return carAttribute == null || carAttribute.isBlank();
    }
}
