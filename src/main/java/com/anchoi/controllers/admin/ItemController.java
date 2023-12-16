package com.anchoi.controllers.admin;

import com.anchoi.config.BusinessException;
import com.anchoi.entity.Item;
import com.anchoi.response.ResponseData;
import com.anchoi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/item")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/province")
    public ResponseEntity getAllItemInProvince(@RequestParam("id") @NotNull String provinceId){
        return ResponseEntity.ok(itemService.getListByProvince(provinceId));
    }

    @GetMapping(value = "/district")
    public ResponseEntity getListByDistrict(@RequestParam("id") @NotNull String districtId){
        return ResponseEntity.ok(itemService.getListByDistrict(districtId));
    }
    @GetMapping(value = "/all")
    public ResponseEntity getItems(){
        return ResponseEntity.ok(itemService.getAllItem());
    }

    @GetMapping(value = "/v1.1/all")
    public ResponseEntity getItemsV1(){
        return ResponseEntity.ok(itemService.getAllItemV1());
    }

    @GetMapping(value = "/detail")
    public ResponseEntity getDetail(@RequestParam("id") @NotNull String id) throws BusinessException {
        return ResponseEntity.ok(itemService.getDetail(id));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteById(@RequestParam("id") String id){
        itemService.deleteItem(id);
        return ResponseEntity.ok(ResponseData.ok("OK"));
    }

    @PostMapping(value = "/add")
    public ResponseEntity save(@RequestBody @NotNull Item item){
        try {
            return ResponseEntity.ok(itemService.save(item));
        } catch (Exception e){
            return ResponseEntity.status(502).body(ResponseData.error(null,e.getMessage()));
        }
    }
}
