package com.ao666.community_background.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private Long userId;
    private String carLicense;
    private LocalDateTime entryTime;


    public Car(String carLicense, LocalDateTime entryTime) {
        this.carLicense = carLicense;
        this.entryTime = entryTime;
        this.userId = 0L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(carLicense, car.carLicense) &&
                Objects.equals(entryTime, car.entryTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carLicense, entryTime);
    }
}
