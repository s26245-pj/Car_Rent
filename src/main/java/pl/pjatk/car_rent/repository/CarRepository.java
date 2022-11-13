package pl.pjatk.car_rent.repository;

import org.springframework.stereotype.Repository;
import pl.pjatk.car_rent.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository {
    private
    List<Car> carList = new ArrayList();


    public void addCar(Car car) throws Exception {
        if (carList.contains(car)) {
            throw new Exception();
        }
        carList.add(car);
    }

    public Optional<Car> findCarById(int id) {
        return carList.stream().filter(it -> it.getId() == id).findFirst();
    }

    public void removeAll(){
        carList = new ArrayList<>();
    }
}
