package com.lms.listeners;

import com.lms.events.NotificationEvent;
import com.lms.manager.NotificationManager;
import com.lms.persistence.Notification;
import com.lms.persistence.OtpRequest;
import com.lms.persistence.User;
import com.lms.service.SmsService;
import com.lms.service.impl.EmailService;
import com.lms.service.impl.ServiceFacade;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


@Component
@AllArgsConstructor
public class NotificationEventListener {
    private static final Logger logger = LoggerFactory.getLogger(NotificationEventListener.class);

    private final EmailService emailService;
    private final SmsService smsService;
    private final ServiceFacade service;
    private final NotificationManager notificationManager;


    @EventListener
    public void handleNotificationEvent(NotificationEvent event) {
        Notification notification = new Notification();
        notification.setUserId(event.getUserId());
        notification.setMessage(event.getMessage());
        notificationManager.addNotification(notification);

        logger.info("Notification Event Received:");
        logger.info("Student ID: {}", event.getUserId());
        logger.info("Message: {}", event.getMessage());
        logger.info("Notification Type: {}", event.getNotificationType());

        // Simulate notification sending
        switch (event.getNotificationType()) {
            case "EMAIL":
                sendEmailNotification(event);
                break;
            case "SMS":
                sendSMSNotification(event);
                break;
            case "IN_APP":
                sendInAppNotification(event);
                break;
        }
    }

    private void sendEmailNotification(NotificationEvent event) {
        User user = service.findUserById(event.getUserId());
        String subject = "Notification for " + event.getUserId() + " \"" + user.getFirstName() + "\"";
        new Thread(() -> {

            try {
                emailService.sendEmail(user.getEmail(), subject, event.getMessage());
            }
            catch (Exception e) {
                logger.error("Couldn't send the email to {}: {}", user.getEmail(), e.getMessage(), e);
            }
        }).start();
    }

    private void sendSMSNotification(NotificationEvent event) {
        User user = service.findUserById(event.getUserId());
        String lessonName = event.getMessage();
        OtpRequest otpRequest = new OtpRequest(
                "maya",
                "+201014367954",
                lessonName
        );
        new Thread(() -> {
            smsService.sendSMS(otpRequest, Optional.of(user));
        }).start();
        logger.info("Sending SMS to student {} for attendance OTP of lesson: {}", event.getUserId(), lessonName);
    }

    private void sendInAppNotification(NotificationEvent event) {
        logger.info("Sending IN-APP notification to student {}: {}", event.getUserId(), event.getMessage());
    }
}