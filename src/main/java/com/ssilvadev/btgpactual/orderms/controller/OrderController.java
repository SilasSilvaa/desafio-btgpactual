package com.ssilvadev.btgpactual.orderms.controller;

import com.ssilvadev.btgpactual.orderms.controller.dto.ApiResponse;
import com.ssilvadev.btgpactual.orderms.controller.dto.OrderResponse;
import com.ssilvadev.btgpactual.orderms.controller.dto.PaginationResponse;
import com.ssilvadev.btgpactual.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable Long customerId,
                                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){

        Page<OrderResponse> pageResponse = orderService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        List<OrderResponse> orderResponses = pageResponse.getContent();
        BigDecimal totalOnOrdersByCustomerId = orderService.findTotalOnOrdersByCustomerId(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnOrders", totalOnOrdersByCustomerId),
                orderResponses,
                PaginationResponse.fromPage(pageResponse)
        ));
    }
}
