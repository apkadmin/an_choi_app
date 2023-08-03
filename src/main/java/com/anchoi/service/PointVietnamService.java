package com.anchoi.service;

import com.anchoi.models.PointVietnamEntity;
import com.anchoi.repository.PointVietnamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PointVietnamService {
    @Autowired
    PointVietnamRepository pointVietnamRepository;

   public List<PointVietnamEntity> getAll(){
        return pointVietnamRepository.findAll();
    }

    @Transactional
    public List<PointVietnamEntity> saveAll(List<PointVietnamEntity> data){
        pointVietnamRepository.deleteAll();
        return  pointVietnamRepository.saveAll(data);
    }

    public List<PointVietnamEntity> getByParent(String parentId){
        return pointVietnamRepository.findAllByParentId(parentId);
    }

    @Transactional
    public List<PointVietnamEntity> saveByParent(List<PointVietnamEntity> data, String parentId){
        pointVietnamRepository.deleteByParentId(parentId);
        return  pointVietnamRepository.saveAll(data);
    }

}
