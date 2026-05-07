package com.anchoi.controllers.app;

import com.anchoi.config.BusinessException;
import com.anchoi.entity.GameUser;
import com.anchoi.entity.GameHint;
import com.anchoi.entity.JigsawDataEntity;
import com.anchoi.entity.Question;
import com.anchoi.response.*;
import com.anchoi.service.*;
import com.anchoi.repository.game.GameHintRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Slf4j
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
    private final GameUserService gameUserService;
    private final GameService gameService;
    private final GameHintRepository gameHintRepository;

    @GetMapping(value = "/language")
    public ResponseEntity<?> getAllLang() {
        return ResponseEntity.ok(ResponseData.ok(languageService.getAllApp()));
    }

    @GetMapping(value = "/category")
    public ResponseEntity<?> getCategory(@RequestParam(value = "type", required = false) String type,
            @RequestHeader(value = "lang", defaultValue = "vi") String lang) {
        if (type != null)
            return ResponseEntity.ok(ResponseData.ok(categoryService.getAllByType(type, lang)));
        return ResponseEntity.ok(ResponseData.ok(categoryService.getAllByLang(lang)));
    }

    @GetMapping("/point")
    public ResponseEntity getALlPoint() {
        return ResponseEntity.ok(ResponseData.ok(pointVietnamService.getAll()));
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
    public ResponseEntity searchAllByName(@RequestHeader(value = "name") String name,
            @RequestHeader(value = "lang", defaultValue = "vi") String lang) {
        return ResponseEntity.ok(ResponseData.ok(searchService.searchAllByName(name, lang)));
    }

    @GetMapping("/province/{id}")
    public ResponseEntity<?> provinceDetail(@PathVariable(value = "id") String id,
            @RequestHeader(value = "lang", defaultValue = "vi") String lang) throws Exception {
        return ResponseEntity.ok(ResponseData.ok(provinceService.findByIdAndLang(id, lang)));
    }

    @GetMapping(value = "/item")
    public ResponseEntity<?> getAllItemInProvinceOrDistrict(
            @RequestParam(value = "provinceId", required = false) String provinceId,
            @RequestParam(value = "districtId", required = false) String districtId,
            @RequestHeader(name = "lang", defaultValue = "vi") String lang) {
        if (provinceId != null)
            return ResponseEntity.ok(ResponseData.ok(itemService.getListByProvince(provinceId, lang)));
        return ResponseEntity.ok(ResponseData.ok(itemService.getListByDistrict(districtId, lang)));
    }

    @GetMapping(value = "/item/{id}")
    public ResponseEntity<?> getDetailItem(@PathVariable(value = "id") String id,
            @RequestHeader(name = "lang", defaultValue = "vi") String lang) {
        return ResponseEntity.ok(ResponseData.ok(itemService.getDetailByLang(id, lang)));
    }

    @GetMapping("/district")
    public ResponseEntity<?> findAllByProvinceId(@RequestParam("provinceId") String provinceId,
            @RequestHeader(value = "lang", defaultValue = "vi") String lang) {
        return ResponseEntity.ok(ResponseData.ok(districtService.findAllByProvinceId(provinceId, lang)));
    }

    @GetMapping("/district/{id}")
    public ResponseEntity<?> districtDetail(@PathVariable("id") String id,
            @RequestHeader(value = "lang", defaultValue = "vi") String lang) {
        return ResponseEntity.ok(ResponseData.ok(districtService.findByIdAndLang(id, lang)));
    }

    @GetMapping("/media")
    @ResponseBody
    public ResponseEntity<?> getFileByReference(@RequestParam("id") @NotBlank String id,
            @RequestHeader(name = "lang", defaultValue = "vi") String lang) {
        return ResponseEntity.ok(ResponseData.ok(mediaService.loadByRefId(id, lang)));
    }

    @GetMapping("/point/{id}")
    public ResponseEntity<?> getByParent(@PathVariable("id") String id,
            @RequestHeader(name = "lang", defaultValue = "vi") String lang) {
        return ResponseEntity.ok(ResponseData.ok(pointVietnamService.getByParentV1(id, lang)));
    }

    /**
     * Get game by ID (legacy)
     * GET /api/app/game/{id}
     */
    @GetMapping("/game/{id}")
    public ResponseEntity<?> getGameById(@PathVariable("id") String id) {
        return ResponseEntity.ok(ResponseData.ok(jigsawService.getById(id)));
    }

    /**
     * Get game detail by ID with all hints
     * GET /api/app/game/detail/{id}
     */
    @GetMapping("/game/detail/{id}")
    public ResponseEntity<?> getGameDetail(@PathVariable("id") String id) throws BusinessException {
        GameResponse gameResponse = gameService.getGameById(id);
//        gameResponse.setDataDetails(null);
        return ResponseEntity.ok(ResponseData.ok(gameResponse));
    }

    /**
     * Get hints for a game detail
     * GET /api/app/game/hint?gameDetailId=xxx
     */
    @GetMapping("/game/hint")
    public ResponseEntity<?> getHints(@RequestParam String gameDetailId) {
        List<GameHint> hints = gameHintRepository.findByGameDetailIdOrderByLevelAsc(gameDetailId);
        return ResponseEntity.ok(ResponseData.ok(hints));

    }

    /**
     * Save game result
     * POST /api/app/game/save
     */
    @PostMapping("/game/save")
    public ResponseEntity<?> saveGameResult(@RequestBody GameUser gameUser) {
        GameUser savedUser = gameUserService.createGameUser(gameUser);
        return ResponseEntity.ok(ResponseData.ok(savedUser));

    }

    /**
     * Create game user
     * POST /api/app/game/user/create
     */
    @PostMapping("/game/user/create")
    public ResponseEntity<?> createGameUser(@RequestBody GameUser gameUser) {
        GameUser savedUser = gameUserService.createGameUser(gameUser);
        return ResponseEntity.ok(ResponseData.ok(savedUser));

    }

    /**
     * Update game user username
     * PUT /api/app/game/user/update-username
     */
    @PutMapping("/game/user/update-username")
    public ResponseEntity<?> updateGameUserUsername(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String username = request.get("username");

        GameUser gameUser = gameUserService.updateUsername(userId, username);
        return ResponseEntity.ok(ResponseData.ok(gameUser));

    }

    @GetMapping("/question")
    public ResponseEntity<?> getQuestionByType(@RequestParam() String type) {
        return ResponseEntity.ok().body(ResponseData.ok(questionService.getByTypeAndHard(type)));
    }

    /**
     * Legacy endpoint - create game user
     * POST /api/app/game/game_users
     */
    @PostMapping("/game/game_users")
    public GameUser createGameUserLegacy(@RequestBody GameUser gameUser) {
        return gameUserService.createGameUser(gameUser);
    }

    @GetMapping("/game/top3/{gameId}")
    public ResponseEntity<List<GameUser>> getTop3ByGameId(@PathVariable String gameId) {
        List<GameUser> top3Users = gameUserService.getTop3ByGameId(gameId);
        if (top3Users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(top3Users);
        }
    }

    @GetMapping("/game/user/{gameId}")
    public ResponseEntity<List<GameUser>> getTop3ByUserIdAndGameId(@PathVariable String gameId,
            @RequestHeader String userId) {
        List<GameUser> top3Games = gameUserService.getTop3ByUserIdAndGameId(gameId, userId);
        if (top3Games.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(top3Games);
        }
    }
}
