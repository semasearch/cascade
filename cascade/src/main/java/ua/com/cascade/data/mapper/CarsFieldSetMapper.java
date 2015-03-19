package ua.com.cascade.data.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import ua.com.cascade.core.model.Car;

public class CarsFieldSetMapper implements FieldSetMapper<Car> {
    public Car mapFieldSet(FieldSet fieldSet) {
        Car car = new Car();
        car.ownerName = fieldSet.readString(1);
        car.ownerDob = fieldSet.readString(2);
        car.carNumber = fieldSet.readString(8);
        car.carModel = fieldSet.readString(9);
        return car;
    }
}

