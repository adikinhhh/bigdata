package com.startup.apigateway.fcm.service;

import com.startup.apigateway.dao.DeviceToken;
import com.startup.apigateway.dao.Location;
import com.startup.apigateway.dao.ParkingLot;
import com.startup.apigateway.dao.User;
import com.startup.apigateway.fcm.model.PushNotificationRequest;
import com.startup.apigateway.repository.ParkingLotRepository;
import com.startup.apigateway.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;


@Service
@Slf4j
public class PushNotificationService {

    private FCMService fcmService;

    private ParkingLotRepository parkingLotRepository;

    private UserRepository userRepository;

    @Autowired
    public PushNotificationService(FCMService fcmService, ParkingLotRepository parkingLotRepository, UserRepository userRepository) {
        this.fcmService = fcmService;
        this.parkingLotRepository = parkingLotRepository;
        this.userRepository = userRepository;
    }

    public void sendPushNotificationForFreeParkingLot(String id, String body) {
        Optional<ParkingLot> crtParkingLot = parkingLotRepository.findById(id);
        if (crtParkingLot.isPresent() && body.contains("Yes")) {
            Location parkingLotLocation = crtParkingLot.get().getLocation();
            //aici trebuia query cu distanta in mongo
            List<User> allUsers = userRepository.findAll();
            allUsers.stream()
                    .filter(user -> Location.getDistanceBetweenLocation(parkingLotLocation, user.getUserLocation()) < 1.0)
                    .map(user -> DeviceToken.builder().deviceToken(user.getDeviceToken()).build())
                    .map(deviceToken -> PushNotificationRequest.builder().title("New free parking lot")
                            .message("{\"description\": \"A new parking lot is available at location\"," + "\"location\":" + parkingLotLocation.toString() + "}")
                            .token(deviceToken.getDeviceToken())
                            .build())
                    .forEach(pushNotificationRequest -> {
                        try {
                            fcmService.sendMessageToToken(pushNotificationRequest);
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    public void sendPushNotification(PushNotificationRequest request) {
        try {
            fcmService.sendMessage(getSamplePayloadData(), request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void sendPushNotificationCustomDataWithTopic(PushNotificationRequest request) {
        try {
            fcmService.sendMessageCustomDataWithTopic(getSamplePayloadDataCustom(), request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void sendPushNotificationCustomDataWithTopicWithSpecificJson(PushNotificationRequest request) {
        try {
            fcmService.sendMessageCustomDataWithTopic(getSamplePayloadDataWithSpecificJsonFormat(), request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void sendPushNotificationWithoutData(PushNotificationRequest request) {
        try {
            fcmService.sendMessageWithoutData(request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        Map<String, String> payload = new HashMap<>();
        Map<String, String> article_data = new HashMap<>();

        pushData.put("title", "Notification for pending work");
        pushData.put("message", "pls complete your pending task immediately");
        pushData.put("image", "https://raw.githubusercontent.com/Firoz-Hasan/SpringBootPushNotification/master/pushnotificationconcept.png");
        pushData.put("timestamp", "2020-07-11 19:23:21");
        pushData.put("article_data", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        // pushData.put("article_data","vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        // payload.put("article_data", String.valueOf(article_data));
        // pushData.put("payload", String.valueOf(payload));

        //   data.put("data", String.valueOf(pushData));
        return pushData;
    }


    private Map<String, String> getSamplePayloadDataCustom() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("title", "Notification for pending work-custom");
        pushData.put("message", "pls complete your pending task immediately-custom");
        pushData.put("image", "https://raw.githubusercontent.com/Firoz-Hasan/SpringBootPushNotification/master/pushnotificationconcept.png");
        pushData.put("timestamp", String.valueOf(new Date()));
        pushData.put("article_data", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        // pushData.put("article_data","vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        return pushData;
    }


    private Map<String, String> getSamplePayloadDataWithSpecificJsonFormat() {
        Map<String, String> pushData = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        ArrayList<Map<String, String>> payload = new ArrayList<>();
        Map<String, String> article_data = new HashMap<>();

        pushData.put("title", "jsonformat");
        pushData.put("message", "itsworkingkudussssssssssssssssssssssssssssssssssss");
        pushData.put("image", "qqq");
        pushData.put("timestamp", "fefe");
        article_data.put("article_data", "ffff");
        payload.add(article_data);
        pushData.put("payload", String.valueOf(payload));
        data.put("data", String.valueOf(pushData));
        return data;

    }

}