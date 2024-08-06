package com.anchoi.controllers.admin;

import com.anchoi.config.BusinessException;
import com.anchoi.entity.Item;
import com.anchoi.request.ItemRequest;
import com.anchoi.response.ResponseData;
import com.anchoi.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping()
    public ResponseEntity findAll(@RequestHeader(name = "lang", defaultValue = "vi") String langCode){
        return ResponseEntity.ok(itemService.findAll(langCode));
    }
    @GetMapping(value = "/province")
    public ResponseEntity getAllItemInProvince(@RequestParam("id") @NotNull String provinceId, @RequestHeader(name = "lang", defaultValue = "vi") String lang){
        return ResponseEntity.ok(itemService.getListByProvince(provinceId, lang));
    }

    @GetMapping(value = "/district")
    public ResponseEntity getListByDistrict(@RequestParam("id") @NotNull String districtId, @RequestHeader(name = "lang", defaultValue = "vi") String lang){
        return ResponseEntity.ok(itemService.getListByDistrict(districtId, lang));
    }


    @GetMapping("{id}")
    public ResponseEntity getDetail(@PathVariable String id) throws BusinessException {
        return ResponseEntity.ok(itemService.getDetail(id));
    }



    @PostMapping(value = "/save")
    public ResponseEntity save(@RequestBody @NotNull ItemRequest item){
        try {
            return ResponseEntity.ok(itemService.save(item));
        } catch (Exception e){
            return ResponseEntity.status(502).body(ResponseData.error(null,e.getMessage()));
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity save(@RequestBody @NotNull ItemRequest item, @RequestParam("id") @NotNull String id){
        try {
            return ResponseEntity.ok(itemService.update(item,id));
        } catch (Exception e){
            return ResponseEntity.status(502).body(ResponseData.error(null,e.getMessage()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable String id){
        itemService.deleteItem(id);
        return ResponseEntity.ok(ResponseData.ok("OK"));
    }
}
