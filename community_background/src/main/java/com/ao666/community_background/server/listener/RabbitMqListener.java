package com.ao666.community_background.server.listener;

import com.ao666.community_background.server.controller.admin.CurrentCarController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {

    @Autowired
    private CurrentCarController currentCarController;

    @RabbitListener(queues = "car_license_queue")
    public void lis(String msg){
        System.out.println("消费者接收到------------------------------" + msg);
        currentCarController.addCar(msg);
    }

}
