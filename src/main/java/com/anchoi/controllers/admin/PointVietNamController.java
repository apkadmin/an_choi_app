package com.anchoi.controllers.admin;

import com.anchoi.models.PointVietnamEntity;
import com.anchoi.service.PointVietnamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api")
public class PointVietNamController {
    @Autowired
    PointVietnamService pointVietnamService;

    @GetMapping("/point")
    public ResponseEntity getALl(){
            return  ResponseEntity.ok(pointVietnamService.getAll());
    }


    @PostMapping("/point")
    public ResponseEntity saveALl(@RequestBody() List<PointVietnamEntity> data){
        return  ResponseEntity.ok(pointVietnamService.saveAll(data));
    }
}
