package com.anchoi.service;

import com.anchoi.models.PointVietnamEntity;
import com.anchoi.repository.PointVietnamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointVietnamService {
    @Autowired
    PointVietnamRepository pointVietnamRepository;

   public List<PointVietnamEntity> getAll(){
        return pointVietnamRepository.findAll();
    }

    public List<PointVietnamEntity> saveAll(List<PointVietnamEntity> data){
        pointVietnamRepository.deleteAll();
        return  pointVietnamRepository.saveAll(data);
    }

}
