package ua.com.cascade.core.service;


import org.springframework.stereotype.Service;
import ua.com.cascade.core.model.Car;
import ua.com.cascade.core.model.Realestate;

@Service
public class DataLookupService {

    public float calculateCarPrice(Car c) {

        float price = 1;
        if (c.carModel.equalsIgnoreCase("Range Rover")) {
            price = 60000;
        }
        if (c.carModel.equalsIgnoreCase("Porshe Cayene")) {
            price = 40000;
        }

        return price;
    }

    public float calculateRealestatePrice(Realestate r) {
        return Float.parseFloat(r.area) * 1000;
    }
}
