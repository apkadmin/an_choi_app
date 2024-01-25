package com.anchoi.repository;

import com.anchoi.response.MediaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mapping.Parameter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommonRepositoryImpl implements CommonRepository{
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<MediaResponse> findAllByIdReferOrderByIndex(String id, String lang) {
        String sql = "SELECT m.url,m.type_Media, m.type, m.id_Refer, m.file_Name, m.index,i18n.description,m.id from media m left join (select description,media_Id from Media_I18n where language_Id =?) i18n on m.id = i18n.media_Id where m.id_Refer=?";

        return jdbcTemplate.query(sql, new Object[]{lang,id}, (rs, rowNum) -> {
            MediaResponse mediaResponse = new MediaResponse();
            mediaResponse.setUrl(rs.getString("url"));
            mediaResponse.setTypeMedia(rs.getString("type_Media"));
            mediaResponse.setType(rs.getString("type"));
            mediaResponse.setIdRefer(rs.getString("id_Refer"));
            mediaResponse.setFileName(rs.getString("file_Name"));
            mediaResponse.setIndex(rs.getInt("index"));
            mediaResponse.setDescription(rs.getString("description"));
            mediaResponse.setId(rs.getString("id"));
            return mediaResponse;
        });

    }

    MediaResponse getResult(ResultSet rs) throws SQLException {
        MediaResponse mediaResponse = new MediaResponse();
        mediaResponse.setTypeMedia(rs.getString("type_Media"));
        mediaResponse.setIndex(rs.getInt("index"));
        mediaResponse.setUrl(rs.getString("url"));
        mediaResponse.setType(rs.getString("type"));
        mediaResponse.setIdRefer(rs.getString("id_refer"));
        mediaResponse.setDescription(rs.getString("description"));
        mediaResponse.setId(rs.getString("id"));
        return mediaResponse;
    }
}
