package com.anchoi.controllers.admin;

import com.anchoi.config.BusinessException;
import com.anchoi.entity.JigsawDataEntity;
import com.anchoi.service.JigsawService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/game")
@RequiredArgsConstructor()
public class JigsawController {
    private final JigsawService jigsawService;
    @GetMapping()
    public ResponseEntity<List<JigsawDataEntity>> getAll(){
        return ResponseEntity.ok(jigsawService.getAll());
    }
    @GetMapping("{id}")
    public ResponseEntity<Optional<JigsawDataEntity>> getById(@PathVariable("id") String id){
        return ResponseEntity.ok(jigsawService.getById(id));
    }

    @PostMapping()
    public ResponseEntity<JigsawDataEntity> saveData(@RequestBody() JigsawDataEntity reqest) throws BusinessException {
        return ResponseEntity.ok(jigsawService.saveData(reqest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteData(@PathVariable("id") String id) {
        jigsawService.deleteById(id);
        return ResponseEntity.ok("Success");
    }
}
