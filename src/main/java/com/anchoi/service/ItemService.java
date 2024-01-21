package com.anchoi.service;

import com.anchoi.common.CommonUtils;
import com.anchoi.config.BusinessException;
import com.anchoi.entity.Item;
import com.anchoi.entity.ItemI18n;
import com.anchoi.repository.item.ItemI18nRepository;
import com.anchoi.repository.item.ItemRepository;
import com.anchoi.request.ItemRequest;
import com.anchoi.response.ItemI18nResponse;
import com.anchoi.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemI18nRepository itemI18nRepository;

    public List<ItemI18nResponse> findAll(String lang){
        return itemRepository.findAll(lang);
    }

    public List<ItemI18nResponse> getListByProvince(String provinceId, String lang){
        return itemRepository.getAllByProvinceId(provinceId,lang);
    }
    public List<ItemI18nResponse> getListByDistrict(String districtId, String lang){
        return itemRepository.getAllByDistrictId(districtId, lang);
    }


    public Item getDetail(String id) throws BusinessException {
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()){
            return item.get();
        }
        throw new BusinessException("500", "Id not exits");
    }

    @Transactional
    public void deleteItem(String itemId){
        itemI18nRepository.deleteAllByItemId(itemId);
        itemRepository.deleteById(itemId);
    }

    @Transactional
    public ItemResponse save(ItemRequest itemRequest){
        Item item = CommonUtils.toObject(itemRequest, Item.class);
        item.setUpdatedDate(new Date());
        item.setCreatedDate(new Date());
        if(!CommonUtils.isEmpty(item.getId())){
            item.setId(UUID.randomUUID().toString());
        }

        List<ItemI18n> itemI18ns = new ArrayList<>();
        if(!CommonUtils.isEmpty(itemRequest.getItemI18ns())){
            itemRequest.getItemI18ns().forEach(i -> {
                ItemI18n itemI18n = CommonUtils.toObject(i, ItemI18n.class);
                if(!CommonUtils.isEmpty(i.getId())) itemI18n.setId(UUID.randomUUID().toString());
                itemI18n.setItemId(item.getId());
                itemI18ns.add(itemI18n);
            });
            itemI18nRepository.saveAll(itemI18ns);
        }
        itemRepository.save(item);

        return CommonUtils.toObject(itemRequest, ItemResponse.class);
    }

    @Transactional
    public ItemResponse update(ItemRequest itemRequest, String id){
        Item item = CommonUtils.toObject(itemRequest, Item.class);
        item.setUpdatedDate(new Date());
        item.setItemI18ns(new ArrayList<>());
        if(!CommonUtils.isEmpty(id)){
            List<ItemI18n> itemI18ns = new ArrayList<>();
            itemI18nRepository.deleteAllByItemId(id);
            itemI18nRepository.flush();
            if(!CommonUtils.isEmpty(itemRequest.getItemI18ns())){
                itemRequest.getItemI18ns().forEach(i -> {
                    ItemI18n itemI18n = CommonUtils.toObject(i, ItemI18n.class);
                    if(!CommonUtils.isEmpty(i.getId())) itemI18n.setId(UUID.randomUUID().toString());
                    itemI18n.setItemId(id);
                    itemI18ns.add(itemI18n);
                });
                itemI18nRepository.saveAll(itemI18ns);
            }
         itemRepository.save(item);
        }

        return CommonUtils.toObject(itemRequest, ItemResponse.class);
    }

}

