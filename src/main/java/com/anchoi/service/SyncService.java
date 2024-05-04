package com.anchoi.service;

import com.anchoi.entity.DistrictI18n;
import com.anchoi.entity.ItemI18n;
import com.anchoi.entity.Post;
import com.anchoi.entity.ProvinceI18n;
import com.anchoi.repository.district.DistrictI18nRepository;
import com.anchoi.repository.item.ItemI18nRepository;
import com.anchoi.repository.post.PostRepository;
import com.anchoi.repository.province.ProvinceI18nRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SyncService {
    private final PostRepository postRepository;
    private final ProvinceI18nRepository provinceI18nRepository;
    private final DistrictI18nRepository districtI18nRepository;
    private final ItemI18nRepository itemI18nRepository;
    @Transactional
    public void syncService(String lang){
     List<Post> postList =  postRepository.findAllByLanguageId(lang);
     List<ProvinceI18n> provinceI18ns = provinceI18nRepository.findAllByLanguageId(lang);
        provinceI18ns.forEach(item ->{
            postList.forEach(x -> {
              item.setDescription(replaceWords(item.getDescription()," "+x.getTitle().trim()," <a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
              item.setDescription(replaceWords(item.getDescription(),","+x.getTitle().trim(),",<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
              item.setDescription(replaceWords(item.getDescription(),"."+x.getTitle().trim(),".<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
              item.setDescription(replaceWords(item.getDescription(),";"+x.getTitle().trim(),";<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
              item.setDescription(replaceWords(item.getDescription(),"\""+x.getTitle().trim(),"\"<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
              item.setDescription(replaceWords(item.getDescription(), "'" +x.getTitle().trim(), "'<a href=\"https://anchoivietnam.com.vn/post/" +x.getGroupId() + "\">"+x.getTitle()+"</a>"));
              item.setDescription(replaceWords(item.getDescription(),"("+x.getTitle().trim(),"(<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
            });
        });
        provinceI18nRepository.saveAll(provinceI18ns);

        List<DistrictI18n> districtI18ns = districtI18nRepository.findAllByLanguageId(lang);
        districtI18ns.forEach(item ->{
            postList.forEach(x -> {
                item.setDescription(replaceWords(item.getDescription()," "+x.getTitle().trim()," <a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
                item.setDescription(replaceWords(item.getDescription(),","+x.getTitle().trim(),",<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
                item.setDescription(replaceWords(item.getDescription(),"."+x.getTitle().trim(),".<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
                item.setDescription(replaceWords(item.getDescription(),";"+x.getTitle().trim(),";<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
                item.setDescription(replaceWords(item.getDescription(),"\""+x.getTitle().trim(),"\"<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
                item.setDescription(replaceWords(item.getDescription(), "'" +x.getTitle().trim(), "'<a href=\"https://anchoivietnam.com.vn/post/" +x.getGroupId() + "\">"+x.getTitle()+"</a>"));
                item.setDescription(replaceWords(item.getDescription(),"("+x.getTitle().trim(),"(<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
            });
        });
        districtI18nRepository.saveAll(districtI18ns);

        List<ItemI18n> itemI18ns = itemI18nRepository.findAllByLanguageId(lang);
        itemI18ns.forEach(item ->{
            postList.forEach(x -> {
                item.setDescription(replaceWords(item.getDescription()," "+x.getTitle().trim()," <a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
                item.setDescription(replaceWords(item.getDescription(),","+x.getTitle().trim(),",<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
                item.setDescription(replaceWords(item.getDescription(),"."+x.getTitle().trim(),".<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
                item.setDescription(replaceWords(item.getDescription(),";"+x.getTitle().trim(),";<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
                item.setDescription(replaceWords(item.getDescription(),"\""+x.getTitle().trim(),"\"<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
                item.setDescription(replaceWords(item.getDescription(), "'" +x.getTitle().trim(), "'<a href=\"https://anchoivietnam.com.vn/post/" +x.getGroupId() + "\">"+x.getTitle()+"</a>"));
                item.setDescription(replaceWords(item.getDescription(),"("+x.getTitle().trim(),"(<a href=\"https://anchoivietnam.com.vn/post/"+x.getGroupId() + "\">"+x.getTitle()+"</a>"));
            });
        });
        itemI18nRepository.saveAll(itemI18ns);
    }


    @Transactional
    public void syncServicePostId(String id){
        Optional<Post> post =  postRepository.findById(id);
        if(post.isPresent()) {
            Post x = post.get();
            List<ProvinceI18n> provinceI18ns = provinceI18nRepository.findAllByLanguageId(x.getLanguageId());
            provinceI18ns.forEach(item -> {
                    item.setDescription(replaceWords(item.getDescription(), " " + x.getTitle().trim(), " <a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "," + x.getTitle().trim(), ",<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "." + x.getTitle().trim(), ".<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), ";" + x.getTitle().trim(), ";<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "\"" + x.getTitle().trim(), "\"<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "'" + x.getTitle().trim(), "'<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "(" + x.getTitle().trim(), "(<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
            });
            provinceI18nRepository.saveAll(provinceI18ns);

            List<DistrictI18n> districtI18ns = districtI18nRepository.findAllByLanguageId(x.getLanguageId());
            districtI18ns.forEach(item -> {

                    item.setDescription(replaceWords(item.getDescription(), " " + x.getTitle().trim(), " <a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "," + x.getTitle().trim(), ",<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "." + x.getTitle().trim(), ".<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), ";" + x.getTitle().trim(), ";<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "\"" + x.getTitle().trim(), "\"<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "'" + x.getTitle().trim(), "'<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "(" + x.getTitle().trim(), "(<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
            });
            districtI18nRepository.saveAll(districtI18ns);

            List<ItemI18n> itemI18ns = itemI18nRepository.findAllByLanguageId(x.getLanguageId());
            itemI18ns.forEach(item -> {
                    item.setDescription(replaceWords(item.getDescription(), " " + x.getTitle().trim(), " <a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "," + x.getTitle().trim(), ",<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "." + x.getTitle().trim(), ".<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), ";" + x.getTitle().trim(), ";<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "\"" + x.getTitle().trim(), "\"<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "'" + x.getTitle().trim(), "'<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "(" + x.getTitle().trim(), "(<a href=\"https://anchoivietnam.com.vn/post/" + x.getGroupId() + "\">" + x.getTitle() + "</a>"));
            });
            itemI18nRepository.saveAll(itemI18ns);
        }
    }

    @Transactional
    public void replaceData(String lang){
     List<Post> postList =  postRepository.findAllByLanguageId(lang);
     List<ProvinceI18n> provinceI18ns = provinceI18nRepository.findAllByLanguageId(lang);
        provinceI18ns.forEach(item ->{
            item.setDescription(replaceWords(item.getDescription(),"https://new.anchoivietnam.com.vn", "https://anchoivietnam.com.vn"));
        });
        provinceI18nRepository.saveAll(provinceI18ns);

        List<DistrictI18n> districtI18ns = districtI18nRepository.findAllByLanguageId(lang);
        districtI18ns.forEach(item ->{
            item.setDescription(replaceWords(item.getDescription(),"https://new.anchoivietnam.com.vn", "https://anchoivietnam.com.vn"));
        });
        districtI18nRepository.saveAll(districtI18ns);

        List<ItemI18n> itemI18ns = itemI18nRepository.findAllByLanguageId(lang);
        itemI18ns.forEach(item ->{
            item.setDescription(replaceWords(item.getDescription(),"https://new.anchoivietnam.com.vn", "https://anchoivietnam.com.vn"));
        });
        itemI18nRepository.saveAll(itemI18ns);
    }


    @Transactional
    public void removeData(String lang){
        List<Post> postList =  postRepository.findAllByLanguageId(lang);
        List<ProvinceI18n> provinceI18ns = provinceI18nRepository.findAllByLanguageId(lang);
        List<String> ids = postList.stream().map(item -> "https://anchoivietnam.com.vn/post/"+  item.getGroupId()).collect(Collectors.toList());
        List<String> newListItem = new ArrayList<>(new HashSet<>(ids));
        provinceI18ns.forEach(item ->{
               item.setDescription(resetWord(item.getDescription(), newListItem));
        });
        provinceI18nRepository.saveAll(provinceI18ns);

        List<DistrictI18n> districtI18ns = districtI18nRepository.findAllByLanguageId(lang);
        districtI18ns.forEach(item ->{
            item.setDescription(resetWord(item.getDescription(), newListItem));
        });
        districtI18nRepository.saveAll(districtI18ns);

        List<ItemI18n> itemI18ns = itemI18nRepository.findAllByLanguageId(lang);
        itemI18ns.forEach(item ->{
            item.setDescription(resetWord(item.getDescription(), newListItem));
        });
        itemI18nRepository.saveAll(itemI18ns);
    }

    public static void main(String[] args) {
        replaceWords("Thanh Hóa là tỉnh cực Bắc miền Trung Việt Nam, nằm ở vị trí trung chuyển giữa các tỉnh phía Bắc và các tỉnh phía Nam. Tỉnh Thanh Hoá có 2 thành phố trực thuộc, 2 thị xã và 23 huyện. Thanh Hoá nằm trong khu vực chịu ảnh hưởng của Vùng kinh tế trọng điểm Bắc Bộ. Với 102 km đường bờ biển ở đây có thể phát triển hoạt động du lịch, khai thác cảng biển; có đường quốc lộ 1A, đường Hồ Chí Minh, đường sắt xuyên Việt và sân bay Thọ Xuân. Thêm vào đó, Thanh Hóa có quy mô diện tích lớn với nhiều vùng sinh thái khác nhau. Địa hình Thanh Hoá khá phức tạp, chia cắt nhiều và thấp dần theo hướng Tây - Đông. Từ phía Tây sang phía Đông có các dải địa hình núi, trung du, đồng bằng và ven biển. Trong tổng diện tích 11.129,48 km2 thì địa hình núi, trung du chiếm 73,3%. Thanh Hoá có 5 hệ thống sông chính là sông Hoạt, sông Mã, sông Yên, sông Lạch Bạng và sông Chàng. Thanh Hoá là cái nôi của nền văn hoá Đông Sơn nổi tiếng. Bãi biển Sầm Sơn là bãi biển đẹp nhất miền Bắc. Thanh Hoá là một trong 4 tỉnh dẫn đầu về phát triển du lịch Việt Nam nhiều di tích lịch sử và danh thắng", "Sông Lạch Bạng", "hello");
    }

    public static String replaceWords(String input, String findWord, String replaceWord) {
        // Tạo biểu thức chính quy không phân biệt chữ hoa chữ thường
        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(findWord) + "\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer();

        // Thực hiện thay thế và xây dựng lại đoạn văn bản mới
        while (matcher.find()) {
            matcher.appendReplacement(result, Matcher.quoteReplacement(replaceWord));
        }
        matcher.appendTail(result);

        return result.toString();
    }

    public static String resetWord(String input,List<String> url){
        String temp= input;
        String regex = "<a\\s+href=\"([^\"]*)\">([^<]*)</a>";
        // Compile regex pattern
        Pattern pattern = Pattern.compile(regex);
        // Create matcher
        Matcher matcher = pattern.matcher(input);
        // Find all matches
        while (matcher.find()) {
            if(!url.contains(matcher.group(1))){
                temp = temp.replaceAll(matcher.group(),matcher.group(2));
            }
        }
        return  temp;
    }


    @Transactional
    public void syncData(String id, String oldName,String name, String lang){
        new Thread(() -> {
            List<ProvinceI18n> provinceI18ns = provinceI18nRepository.findAllByLanguageId(lang);
            provinceI18ns.forEach(item ->{
                if(name != null && !name.isEmpty()) {
                    item.setDescription(replaceWords(item.getDescription(), " " + name.trim(), " <a href=\"https://anchoivietnam.com.vn/post/" + id + "\">" + name + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(),","+name.trim(),",<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(),"."+name.trim(),".<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(),";"+name.trim(),";<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(),"\""+name.trim(),"\"<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "'" +name.trim(), "'<a href=\"https://anchoivietnam.com.vn/post/" +id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(),"("+name.trim(),"(<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));

                }
                if(oldName != null && !oldName.isEmpty() && !oldName.equals(name)){
                    item.setDescription(replaceWords(item.getDescription(),",<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),".<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),";<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),"\"<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),"'<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),"(<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription()," <a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                }
            });
            provinceI18nRepository.saveAll(provinceI18ns);

            List<DistrictI18n> districtI18ns = districtI18nRepository.findAllByLanguageId(lang);
            districtI18ns.forEach(item ->{
                if(name != null && !name.isEmpty()) {
                    item.setDescription(replaceWords(item.getDescription(), " " + name.trim(), " <a href=\"https://anchoivietnam.com.vn/post/" + id + "\">" + name + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(),","+name.trim(),",<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(),"."+name.trim(),".<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(),";"+name.trim(),";<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(),"\""+name.trim(),"\"<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "'" +name.trim(), "'<a href=\"https://anchoivietnam.com.vn/post/" +id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(),"("+name.trim(),"(<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                }
                if(oldName != null && !oldName.isEmpty() && !oldName.equals(name)){
                    item.setDescription(replaceWords(item.getDescription(),",<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),".<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),";<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),"\"<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),"'<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),"(<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription()," <a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                }
            });
            districtI18nRepository.saveAll(districtI18ns);

            List<ItemI18n> itemI18ns = itemI18nRepository.findAllByLanguageId(lang);
            itemI18ns.forEach(item ->{
                if(name != null && !name.isEmpty()) {
                    item.setDescription(replaceWords(item.getDescription(), " " + name.trim(), " <a href=\"https://anchoivietnam.com.vn/post/" + id + "\">" + name + "</a>"));
                    item.setDescription(replaceWords(item.getDescription(),","+name.trim(),",<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(),"."+name.trim(),".<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(),";"+name.trim(),";<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(),"\""+name.trim(),"\"<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(), "'" +name.trim(), "'<a href=\"https://anchoivietnam.com.vn/post/" +id + "\">"+name+"</a>"));
                    item.setDescription(replaceWords(item.getDescription(),"("+name.trim(),"(<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+name+"</a>"));
                }
                if(oldName != null && !oldName.isEmpty() && !oldName.equals(name)){
                    item.setDescription(replaceWords(item.getDescription(),",<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),".<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),";<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),"\"<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),"'<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription(),"(<a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                    item.setDescription(replaceWords(item.getDescription()," <a href=\"https://anchoivietnam.com.vn/post/"+id + "\">"+oldName.trim()+"</a>"," " + oldName.trim()));
                }
            });
            itemI18nRepository.saveAll(itemI18ns);
        }).run();

    }

}
