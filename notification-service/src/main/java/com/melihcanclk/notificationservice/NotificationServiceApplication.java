package com.melihcanclk.notificationservice;

import com.melihcanclk.notificationservice.events.OrderPlacedEvent;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Map;
import java.util.Objects;

@SpringBootApplication
@EnableDiscoveryClient
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceApplication {

	private final ObservationRegistry observationRegistry;
	private final Tracer tracer;

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}


	@KafkaListener(topics = "notificationTopic")
	public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
		Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
			log.info("Got message <{}>", orderPlacedEvent);
			log.info("TraceId- {}, Received Notification for Order - {}", Objects.requireNonNull(this.tracer.currentSpan()).context().traceId(),
					orderPlacedEvent.getOrderNumber());
		});
		// send out an email notification
	}
}
