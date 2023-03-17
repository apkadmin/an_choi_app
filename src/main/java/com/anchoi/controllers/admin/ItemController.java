package com.anchoi.controllers.admin;

import com.anchoi.models.Item;
import com.anchoi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(value = "/api/item")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/province")
    public ResponseEntity getAllItemInProvnice(@RequestParam("id") @NotNull String provinceId){
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
    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteById(@RequestParam("id") String id){
        itemService.deleteItem(id);
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/add")
    public ResponseEntity save(@RequestBody @NotNull Item item){
        try {
            return ResponseEntity.ok(itemService.save(item));
        } catch (Exception e){
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }
}
