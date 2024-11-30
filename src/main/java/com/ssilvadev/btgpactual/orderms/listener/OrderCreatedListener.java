package com.ssilvadev.btgpactual.orderms.listener;

import com.ssilvadev.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import com.ssilvadev.btgpactual.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.ssilvadev.btgpactual.orderms.config.RabbitMqConfig.ORDER_CREATE_QUEUE;

@Component
@RequiredArgsConstructor
public class OrderCreatedListener {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final OrderService orderService;

    @RabbitListener(queues = ORDER_CREATE_QUEUE)
    public void listen(Message<OrderCreatedEvent> message){
        logger.info("Message consumed: {}", message);

        orderService.save(message.getPayload());
    }
}
