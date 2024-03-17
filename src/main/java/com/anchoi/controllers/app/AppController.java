package com.anchoi.controllers.app;

import com.anchoi.config.BusinessException;
import com.anchoi.entity.JigsawDataEntity;
import com.anchoi.entity.Question;
import com.anchoi.response.DistrictI18nResponse;
import com.anchoi.response.ProvinceI18nResponse;
import com.anchoi.response.ResponseData;
import com.anchoi.response.SearchResponse;
import com.anchoi.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/app")
@RequiredArgsConstructor
public class AppController {
    private final SearchService searchService;
    private final LanguageService languageService;
    private final CategoryService categoryService;
    private final PointVietnamService pointVietnamService;
    private final ProvinceService provinceService;
    private final ItemService itemService;
    private final DistrictService districtService;
    private final MediaService mediaService;
    private final JigsawService jigsawService;
    private final QuestionService questionService;



    @GetMapping(value = "/language")
    public ResponseEntity<?> getAllLang(){
        return ResponseEntity.ok(ResponseData.ok(languageService.getAll()));
    }

    @GetMapping(value = "/category")
    public ResponseEntity<?> getCategory(@RequestParam(value = "type", required = false) String type, @RequestHeader(value = "lang", defaultValue = "vi") String lang) {
      if(type != null) return ResponseEntity.ok(ResponseData.ok(categoryService.getAllByType(type,lang)));

      return ResponseEntity.ok(ResponseData.ok(categoryService.getAllByLang(lang)));
    }

    @GetMapping("/point") //danh sach cac diem de tren dat nc vn
    public ResponseEntity getALlPoint(){
        return  ResponseEntity.ok(ResponseData.ok(pointVietnamService.getAll()));
    }

    @GetMapping("/province")
    public ResponseEntity<?> findAllProvince(@RequestHeader(value = "lang", defaultValue = "vi") String lang) {
        return ResponseEntity.ok(ResponseData.ok(provinceService.findAll(lang)));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getDataSearch(@RequestHeader(value = "lang", defaultValue = "vi") String lang) {
        return ResponseEntity.ok(ResponseData.ok(searchService.searchAllApp(lang)));
    }

    @GetMapping("/search-by-name")
    public ResponseEntity searchAllByName(@RequestHeader(value = "name") String name, @RequestHeader(value = "lang", defaultValue = "vi") String lang) {
        return ResponseEntity.ok(ResponseData.ok(searchService.searchAllByName(name, lang)));
    }

    @GetMapping("/province/{id}")
    public ResponseEntity<?> provinceDetail(@PathVariable(value = "id") String id,@RequestHeader(value = "lang", defaultValue = "vi") String lang) throws Exception {
        return ResponseEntity.ok(ResponseData.ok(provinceService.findByIdAndLang(id,lang)));
    }

    @GetMapping(value = "/item")
    public ResponseEntity<?> getAllItemInProvinceOrDistrict(@RequestParam(value = "provinceId", required = false) String provinceId, @RequestParam(value = "districtId", required = false) String districtId, @RequestHeader(name = "lang", defaultValue = "vi") String lang){
        if(provinceId != null) return ResponseEntity.ok(ResponseData.ok(itemService.getListByProvince(provinceId, lang)));
        return ResponseEntity.ok(ResponseData.ok(itemService.getListByDistrict(districtId, lang)));
    }


    @GetMapping(value = "/item/{id}")
    public ResponseEntity<?> getDetailItem(@PathVariable(value = "id") String id, @RequestHeader(name = "lang", defaultValue = "vi") String lang){
        return ResponseEntity.ok(ResponseData.ok(itemService.getDetailByLang(id, lang)));
    }


    @GetMapping("/district")
    public ResponseEntity<?> findAllByProvinceId(@RequestParam("provinceId") String provinceId, @RequestHeader(value = "lang", defaultValue = "vi") String lang){
        return ResponseEntity.ok(ResponseData.ok(districtService.findAllByProvinceId(provinceId, lang)));
    }

    @GetMapping("/district/{id}")
    public ResponseEntity<?> districtDetail(@PathVariable("id") String id, @RequestHeader(value = "lang", defaultValue = "vi") String lang) {
        return ResponseEntity.ok(ResponseData.ok(districtService.findByIdAndLang(id, lang)));
    }

    @GetMapping("/media")
    @ResponseBody
    public ResponseEntity<?> getFileByReference(@RequestParam("id") @NotBlank String id, @RequestHeader(name = "lang", defaultValue = "vi") String lang) {
        return ResponseEntity.ok(ResponseData.ok(mediaService.loadByRefId(id, lang)));
    }

    @GetMapping("/point/{id}")
    public ResponseEntity<?> getByParent( @PathVariable("id") String id, @RequestHeader(name = "lang", defaultValue = "vi") String lang){
        return  ResponseEntity.ok(ResponseData.ok(pointVietnamService.getByParentV1(id, lang)));
    }


    @GetMapping("/game/{id}")
    public ResponseEntity<?> getGameById(@PathVariable("id") String id){
        return ResponseEntity.ok(ResponseData.ok(jigsawService.getById(id)));
    }

    @GetMapping("/question")
    public ResponseEntity<?> getQuestionByType(@RequestParam()  String type) {
        return ResponseEntity.ok().body(ResponseData.ok(questionService.getByTypeAndHard(type)));
    }

}
