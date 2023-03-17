package com.anchoi.service;

import com.anchoi.models.Item;
import com.anchoi.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getListByProvince(String provinceId){
        return itemRepository.getAllByProvinceId(provinceId);
    }
    public List<Item> getListByDistrict(String districtId){
        return itemRepository.getAllByDistrictId(districtId);
    }

    public List<Item> getAllItem(){
        return itemRepository.findAll();
    }

    public void deleteItem(String itemId){
        itemRepository.deleteById(itemId);
    }

    public Item save(Item item){
       if(item != null){
           itemRepository.save(item);
       }

       return item;
    }
}

