package com.panhareach.SpringBackend.service;

import com.panhareach.SpringBackend.exception.AlreadyExistException;
import com.panhareach.SpringBackend.exception.BadRequestException;
import com.panhareach.SpringBackend.exception.NotFoundException;
import com.panhareach.SpringBackend.model.entity.AddressEntity;
import com.panhareach.SpringBackend.model.entity.CategoryEntity;
import com.panhareach.SpringBackend.model.entity.UserEntity;
import com.panhareach.SpringBackend.model.request.user.UserRequest;
import com.panhareach.SpringBackend.model.request.user.UserRestoreRequest;
import com.panhareach.SpringBackend.repository.UserRepository;
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
public class UserService {
    private final UserRepository userRepository;

    public UserEntity create(UserRequest request) throws Exception{
        UserEntity user = request.toEntity();

        if(this.userRepository.existsByUsernameAndDeletedAtIsNull(user.getUsername())){
            throw new AlreadyExistException("this username already exist");
        }


        try{
            return this.userRepository.save(user);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    public Page<UserEntity> findAll(int page, int limit, String sort, boolean isPage, boolean isTrash, Map<String, String> reqParam ) throws Exception{
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
        return this.userRepository.findAll((Specification<UserEntity>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for(Map.Entry<String, String> entry :reqParam.entrySet()){
                if(entry.getKey().startsWith("q_")){
                    String qkey = entry.getKey().split("q_", 2)[1];
                    String qValue = entry.getValue() == null ? "" : entry.getValue();

                    predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(qkey).as(String.class)), "%" +qValue.toUpperCase() + "%"));

                }
            };

            if(predicates.size() == 0) predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("id").as(String.class)), "%" +""+ "%"));

            return criteriaBuilder.and(isTrash ? criteriaBuilder.isNotNull(root.get("deletedAt")) : criteriaBuilder.isNull(root.get("deletedAt")), criteriaBuilder.or(predicates.toArray(Predicate[]::new)));
        }, pageable);
    }

    public UserEntity delete(Long id) throws Exception{
        UserEntity data = this.findOne(id);

        try {
            data.setDeletedAt(new Date());
            return this.userRepository.save(data);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    public UserEntity findOne(Long id) throws Exception{
        UserEntity data = this.userRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new NotFoundException("User not found"));

        return data;
    }

    public UserEntity update(Long id, UserRequest request) throws Exception {
        UserEntity data = this.findOne(id);
        AddressEntity address = new AddressEntity();

        if(data.getUsername() != request.getUsername()){
            if(this.userRepository.existsByUsernameAndDeletedAtIsNull(request.getUsername())){
                throw new AlreadyExistException("User's name already exist");
            }
        }
//        data = request.toEntity();
        data.setUsername(request.getUsername());
        address.setUser(data);
        address.setAddress(request.getAddress().getAddress());
        data.setAddressEntity(address);

        try{
            return this.userRepository.save(data);
        }catch (Exception ex){
            throw new Exception(ex);
        }

    }

    public UserEntity restore(Long id, UserRestoreRequest req) throws Exception {
        UserEntity data = this.userRepository.findByIdAndDeletedAtIsNotNull(id).orElseThrow(()-> new NotFoundException("User Not Found"));

        if(userRepository.existsByUsernameAndDeletedAtIsNull(req.getUsername())){
            throw new AlreadyExistException("User's name already exist");
        }

        data.setDeletedAt(null);
        data.setUsername(req.getUsername());
        try {
            return this.userRepository.save(data);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }
}

