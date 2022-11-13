package pl.pjatk.car_rent.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.pjatk.car_rent.exceptions.ValidationException;
import pl.pjatk.car_rent.model.Car;
import pl.pjatk.car_rent.model.CarClass;
import pl.pjatk.car_rent.repository.CarRepository;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RentalServiceTest {

    private static RentalService rentalService;
    private static CarRepository carRepository;

    @BeforeAll // metoda wywołana przed wszystkimi, tylko raz.
    public static void setup() {
        carRepository = new CarRepository();
        rentalService = new RentalService(carRepository);
    }

    @BeforeEach
    public void cleanAll(){
        carRepository.removeAll();
    }

    @Test
    public void shouldAddNewCar() {
        Car car = new Car(1,
                "Mercedes",
                "Benz",
                "AHDJ2125WR",
                CarClass.BIEDA);
        assertDoesNotThrow(()->rentalService.addNewCar(car));
    }

    @ParameterizedTest
    @MethodSource("provideCarsWithEmptyVin")
    public void shouldThrowExceptionWhenAddingCarWithEmptyVin(){
        Car car = new Car(1,
                "Mercedes",
                "Benz",
                "",
                CarClass.BIEDA);

        try {
            rentalService.addNewCar(car);
        } catch (ValidationException exc){
            System.out.println(exc.getMessage());
        }

        assertThrows(ValidationException.class, () -> rentalService.addNewCar(car), "Your wife is dead");

    }
    private static Stream<Arguments> provideCarsWithEmptyVin(){
        return Stream.of(
                Arguments.of(new Car(
                        1,
                        "Mercedes",
                        "Benz",
                        "",
                        CarClass.BIEDA
                )),
                Arguments.of(2, "Skoda", "superb", "", CarClass.PRESTIZ),
                Arguments.of(3, "Fiat", "126p", "", CarClass.PREMIUM)
        );
    }

    @Test
    public void shouldFindCar() throws Exception {
        Car car = new Car(1,
                "Mercedes",
                "Benz",
                "",
                CarClass.BIEDA
        );

        carRepository.addCar(car);

        Optional<Car> foundCar = rentalService.findCarById(1);

        assertTrue(foundCar.isPresent());
        assertEquals(car, foundCar.get());
    }
    @Test
    public void shouldReturnEmptyOptionalWhenCarIsNotPresent() {
        Optional<Car> foundCar = rentalService.findCarById(1);

        assertFalse(foundCar.isEmpty());
        assertEquals(Optional.empty(), foundCar.get());
    }
}