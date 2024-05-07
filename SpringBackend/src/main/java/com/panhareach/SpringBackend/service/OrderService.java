package com.panhareach.SpringBackend.service;

import com.panhareach.SpringBackend.exception.AlreadyExistException;
import com.panhareach.SpringBackend.exception.BadRequestException;
import com.panhareach.SpringBackend.exception.NotFoundException;
import com.panhareach.SpringBackend.model.entity.OrderEntity;
import com.panhareach.SpringBackend.model.entity.OrderDetailEntity;
import com.panhareach.SpringBackend.model.request.category.CategoryRestoreRequest;
import com.panhareach.SpringBackend.model.request.order.OrderRequest;
import com.panhareach.SpringBackend.model.request.orderDetail.OrderDetailRequest;
import com.panhareach.SpringBackend.repository.OrderRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderEntity create(OrderRequest req) throws Exception{
        OrderEntity order = req.toEntity();
        Double totalPrice = 0.0;

        List<OrderDetailEntity> orderDetail = new ArrayList<>();

        for(OrderDetailRequest data : req.getOrderDetail()){
            totalPrice += data.getPrice() * data.getQuantity();

            OrderDetailEntity ordDetail = new OrderDetailEntity();

            ordDetail.setProductName(data.getProductName());
            ordDetail.setQty(data.getQuantity());
            ordDetail.setPrice(data.getPrice());
            ordDetail.setOrder(order);

            orderDetail.add(ordDetail);
        }
        order.setTotalPrice(totalPrice);
        order.setOrderDetails(orderDetail);

        try{
            return this.orderRepository.save(order);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    public Page<OrderEntity> findAll(int page, int limit, String sort, boolean isPage, Map<String, String> reqParam ) throws Exception{
        if(page <= 0 || limit <= 0) {
            throw new BadRequestException("Invalid Pagination");
        }

        List<Sort.Order> sortByList = new ArrayList<>();
        for(String srt: sort.split(",")){
            String []arr = srt.split(":");
            if(arr.length < 2){
                continue;
            }
            String field = arr[0];
            String direction = arr[1].toLowerCase();

            sortByList.add(new Sort.Order( direction.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, field));
        }

        Pageable pageable;
        if(isPage) pageable = PageRequest.of(page -1, limit, Sort.by(sortByList));
        else pageable = Pageable.unpaged();
        return this.orderRepository.findAll((Specification<OrderEntity>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for(Map.Entry<String, String> entry :reqParam.entrySet()){
                if(entry.getKey().startsWith("q_")){
                    String qkey = entry.getKey().split("q_", 2)[1];
                    String qValue = entry.getValue() == null ? "" : entry.getValue();

                    predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(qkey).as(String.class)), "%" +qValue.toUpperCase() + "%"));

                }
            };

            if(predicates.size() == 0) predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("id").as(String.class)), "%" +""+ "%"));

            return criteriaBuilder.or(predicates.toArray(Predicate[]::new));
        }, pageable);
    }

    public OrderEntity deleteByid(Long id) throws Exception{
        OrderEntity data = this.findOne(id);

        try {
            this.orderRepository.deleteById(id);
            return data;
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }


    public OrderEntity findOne(Long id) throws Exception{
        OrderEntity data = this.orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));

        return data;
    }

    public OrderEntity update(Long id, OrderRequest req) throws Exception {
        Double totalPrice = 0.0;
        List<OrderDetailEntity> orderDetail = new ArrayList<>();

        OrderEntity data = this.findOne(id);


        data.setCustomerName(req.getCustomerName());

        for(OrderDetailRequest dataDetail : req.getOrderDetail()){
            totalPrice += dataDetail.getPrice() * dataDetail.getQuantity();

            OrderDetailEntity ordDetail = new OrderDetailEntity();

            ordDetail.setProductName(dataDetail.getProductName());
            ordDetail.setQty(dataDetail.getQuantity());
            ordDetail.setPrice(dataDetail.getPrice());
            ordDetail.setOrder(data);

            orderDetail.add(ordDetail);
        }
        data.setTotalPrice(totalPrice);
        data.setOrderDetails(orderDetail);

        try{
            return this.orderRepository.save(data);
        }catch (Exception ex){
            throw new Exception(ex);
        }

    }


}
