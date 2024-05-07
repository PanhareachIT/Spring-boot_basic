package com.panhareach.SpringBackend.service;

import com.panhareach.SpringBackend.exception.AlreadyExistException;
import com.panhareach.SpringBackend.exception.BadRequestException;
import com.panhareach.SpringBackend.exception.NotFoundException;
import com.panhareach.SpringBackend.model.entity.OrderDetailEntity;
import com.panhareach.SpringBackend.model.entity.ProductEntity;

import com.panhareach.SpringBackend.model.entity.TagsEntity;
import com.panhareach.SpringBackend.model.request.order.OrderRequest;
import com.panhareach.SpringBackend.model.request.orderDetail.OrderDetailRequest;
import com.panhareach.SpringBackend.model.request.product.ProductRequest;

import com.panhareach.SpringBackend.repository.ProductRepository;
import com.panhareach.SpringBackend.repository.TagRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final TagRepository tagRepository;

    public ProductEntity create(ProductRequest req) throws Exception{
        ProductEntity product = req.toEntity();


        if(productRepository.existsByName(req.getName())){
            throw new AlreadyExistException("Product already Exists");
        }

        List<TagsEntity> tags = this.tagRepository.findAllById(req.getTagId());
        product.setTags(Set.copyOf(tags));
        try{
            return this.productRepository.save(product);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    public Page<ProductEntity> findAll(int page, int limit, String sort, boolean isPage, Map<String, String> reqParam ) throws Exception{
        if(page <= 0 || limit < 1) {
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
        return this.productRepository.findAll((Specification<ProductEntity>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for(Map.Entry<String, String> entry :reqParam.entrySet()){
                if(entry.getKey().startsWith("q_")){
                    String qkey = entry.getKey().split("q_", 2)[1];
                    String qValue = entry.getValue() == null ? "" : entry.getValue();

                    predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(qkey).as(String.class)), "%" +qValue.toUpperCase() + "%"));

                }
            };

            if(predicates.size() == 0) predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name").as(String.class)), "%" +""+ "%"));

            return criteriaBuilder.or(predicates.toArray(Predicate[]::new));
        }, pageable);
    }

    public ProductEntity deleteById(Long id) throws Exception{
        ProductEntity data = this.findOne(id);

        try {
            this.productRepository.deleteById(id);
            return data;
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }


    public ProductEntity findOne(Long id) throws Exception{
        ProductEntity data = this.productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));

        return data;
    }

    public ProductEntity update(Long id, ProductRequest req) throws Exception {
        ProductEntity product = this.findOne(id);
        if(product.getName()!=req.getName()){
            if(this.productRepository.existsByName(req.getName())){
                throw new AlreadyExistException("Product Already Exist");
            }
        }


        product.setName(req.getName());
        product.setPrice(req.getPrice());
        product.setDesciption(req.getDescription());

        List<TagsEntity> tags = this.tagRepository.findAllById(req.getTagId());
        product.setTags(Set.copyOf(tags));
        try{
            return  this.productRepository.save(product);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }


}