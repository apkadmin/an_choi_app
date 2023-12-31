package com.anchoi.repository.district;

import com.anchoi.config.BusinessException;
import com.anchoi.repository.district.DistrictRepositoryCustomer;
import com.anchoi.response.DistrictResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class DistrictRepositoryCustomerImpl implements DistrictRepositoryCustomer {
    @Autowired
    EntityManager entityManager;
    @Override
    public List<DistrictResponse> findAllWithProvinceName() throws BusinessException {
//        try {
//            String queryBuilder = "select d.id,d.name,d.population,d.density,d.year_of_density as yearOfDensity, d.coastline as coastline, " +
//                    " d.description, d.latitude,d.longitude,d.map_image as mapImage,d.created_date as createdDate,  d.created_by as createdBy, d.updated_date as updatedDate, d.updated_by as updatedBy, " +
//                    "  d.province_id as provinceId, p.name as provinceName, d.url_audio as urlAudio, d.square_area as squareArea" +
//                    " from district d " +
//                    "left join province p on d.province_id = p.id ";
//            Query query = entityManager.createNativeQuery(queryBuilder, "FIND_ALL_WITH_PROVINCE_NAME");
//
//            return query.getResultList() != null ? query.getResultList() : new ArrayList<>();
//        } catch (Exception e) {
//            throw new BusinessException("500", e.getMessage());
//        }
        return new ArrayList<>();

    }
}
