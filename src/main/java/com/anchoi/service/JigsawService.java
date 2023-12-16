package com.anchoi.service;

import com.anchoi.config.BusinessException;
import com.anchoi.entity.JigsawDataEntity;
import com.anchoi.repository.JigsawDetailRepository;
import com.anchoi.repository.JigsawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JigsawService {
    private final JigsawRepository jigsawRepository;
    private final JigsawDetailRepository jigsawDetailRepository;
    public Optional<JigsawDataEntity> getById(String id){
        return jigsawRepository.findById(id);
    }

    public List<JigsawDataEntity> getAll(){
        List<JigsawDataEntity> data = jigsawRepository.findAll();
        data.forEach(item -> { item.setDataDetails(new ArrayList<>());
        item.setImage("");
        });
        return  data;
    }

    public void deleteById(String id){
         jigsawRepository.deleteById(id);
    }
    @Transactional
    public JigsawDataEntity saveData(JigsawDataEntity request) throws BusinessException{
        jigsawDetailRepository.deleteAllByParentId(request.getId());
        if(request != null){
            JigsawDataEntity res =  jigsawRepository.save(request);
            return res;
        }
        throw new BusinessException("500","Không có dữ liệu");
    }

}
