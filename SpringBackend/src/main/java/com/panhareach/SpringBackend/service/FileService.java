package com.panhareach.SpringBackend.service;

import com.panhareach.SpringBackend.exception.NotFoundException;
import com.panhareach.SpringBackend.model.entity.FileEntity;
import com.panhareach.SpringBackend.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {
    private final FileRepository fileRepository;
    private final StorageService storageService;

    public FileEntity upload(MultipartFile file) throws Exception {
        String fileName = this.storageService.upload(file);
        FileEntity f = new FileEntity();
        f.setName(fileName);
        f.setSize(file.getSize());
        f.setType(file.getContentType());
        f.setOriginalName(file.getOriginalFilename());
        try{
            return this.fileRepository.save(f);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    public List<FileEntity> batchUpload(List<MultipartFile> files) throws Exception {
        List<FileEntity> fileEntities = new ArrayList<>();
        for(MultipartFile file:files){
            fileEntities.add(this.upload(file));
        }
        return fileEntities;
    }

    public FileEntity findOne(Long id) throws NotFoundException {
        return this.fileRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new NotFoundException("File Not Found"));
    }

    public FileEntity delete(Long id) throws Exception {
        FileEntity entity = this.findOne(id);

        entity.setDeletedAt(new Date());

        try {
            return this.fileRepository.save(entity);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    @Transactional
    public FileEntity froceDelete(Long id) throws Exception {
        FileEntity entity = this.fileRepository.findById(id).orElseThrow(() -> new NotFoundException("File Not Found"));

        this.storageService.deleteFile(entity.getName());

        try {
            this.fileRepository.deleteById(id);
            return entity;
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }

    public FileEntity restore(Long id) throws Exception {
        FileEntity entity = this.fileRepository.findByIdAndDeletedAtIsNotNull(id);

        entity.setDeletedAt(null);
        try {
            return this.fileRepository.save(entity);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }


    public Page<FileEntity> findAll(int page, int limit, boolean isTrash, String q){
        if(isTrash){
            return this.fileRepository.findAllByOriginalNameContainsAndDeletedAtIsNotNull(q, PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt")));
        }else {
            return this.fileRepository.findAllByOriginalNameContainsAndDeletedAtIsNull(q, PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt")));
        }
    }
}
