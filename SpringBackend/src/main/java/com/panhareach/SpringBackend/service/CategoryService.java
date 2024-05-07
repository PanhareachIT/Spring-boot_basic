package com.panhareach.SpringBackend.service;

import com.panhareach.SpringBackend.exception.AlreadyExistException;
import com.panhareach.SpringBackend.exception.BadRequestException;
import com.panhareach.SpringBackend.exception.NotFoundException;
import com.panhareach.SpringBackend.model.entity.CategoryEntity;
import com.panhareach.SpringBackend.model.request.category.CategoryRequest;
import com.panhareach.SpringBackend.model.request.category.CategoryRestoreRequest;
import com.panhareach.SpringBackend.repository.CategoryRepository;
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

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;


    public CategoryEntity create(CategoryRequest req) throws Exception{
        CategoryEntity category = req.toEntity();


        if(categoryRepository.existsByNameAndDeletedAtIsNull(category.getName())){
            throw new AlreadyExistException("Category already Exists");
        }
        try{
            return this.categoryRepository.save(category);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    public Page<CategoryEntity> findAll(int page, int limit, String sort, boolean isPage, boolean isTrash, Map<String, String> reqParam ) throws Exception{
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
        return this.categoryRepository.findAll((Specification<CategoryEntity>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for(Map.Entry<String, String> entry :reqParam.entrySet()){
                if(entry.getKey().startsWith("q_")){
                    String qkey = entry.getKey().split("q_", 2)[1];
                    String qValue = entry.getValue() == null ? "" : entry.getValue();

                    predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(qkey).as(String.class)), "%" +qValue.toUpperCase() + "%"));

                }
            };

            if(predicates.size() == 0) predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name").as(String.class)), "%" +""+ "%"));

            return criteriaBuilder.and(isTrash ? criteriaBuilder.isNotNull(root.get("deletedAt")) : criteriaBuilder.isNull(root.get("deletedAt")), criteriaBuilder.or(predicates.toArray(Predicate[]::new)));
            }, pageable);
    }

    public CategoryEntity delete(Long id) throws Exception{
        CategoryEntity data = this.findOne(id);

        try {
            data.setDeletedAt(new Date());
            return this.categoryRepository.save(data);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    public CategoryEntity findOne(Long id) throws Exception{
        return this.categoryRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new NotFoundException("Category not found"));

//        return data;
    }

    public CategoryEntity update(Long id, CategoryRequest request) throws Exception {
        CategoryEntity data = this.findOne(id);

        if(data.getName() != request.getName()){
            if(this.categoryRepository.existsByNameAndDeletedAtIsNull(request.getName())){
                throw new AlreadyExistException("Category's name already exist");
            }
        }

        data.setName(request.getName());
        data.setDescription(request.getDescription());

        try{
            return this.categoryRepository.save(data);
        }catch (Exception ex){
            throw new Exception(ex);
        }

    }

    public CategoryEntity restore(Long id, CategoryRestoreRequest req) throws Exception {
        CategoryEntity data = this.findOne(id);

        if(categoryRepository.existsByNameAndDeletedAtIsNull(req.getName())){
            throw new AlreadyExistException("Category's name already exist");
        }

        data.setDeletedAt(null);
        data.setName(req.getName());
        try {
            return this.categoryRepository.save(data);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

}
