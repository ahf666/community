package com.ao666.community_background.common.util;

import com.ao666.community_background.pojo.vo.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Slf4j
@Builder
public class RedisUtil {
    // 获得排序链表
    public static LinkedList<Car> sortAndDeduplicateCars(Map<String, LocalDateTime> currentCar) {
        return currentCar.entrySet().stream()
                .map(entry -> new Car(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(Car::getEntryTime))
                .distinct()
                .collect(Collectors.toCollection(LinkedList::new));
    }

    // 分页查询,返回数组列表
    public static List<Car> paginateCars(List<Car> cars, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        if (fromIndex >= cars.size()) {
            return Collections.emptyList(); // 或者可以抛出一个异常，表示页码超出范围
        } else {
            int toIndex = Math.min(fromIndex + pageSize, cars.size());
            // 使用 subList 获取子集，并将其转换为 ArrayList
            List<Car> carSubList = cars.subList(fromIndex, toIndex);
            return new ArrayList<>(carSubList);
        }
    }
}
