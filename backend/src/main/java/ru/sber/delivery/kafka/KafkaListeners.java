package ru.sber.delivery.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListeners {
    @KafkaListener(topics = "awaiting-delivery", groupId = "selfMessages")
    void listener(String data) {
        log.info("Listener получил: " + data);
    }
}
