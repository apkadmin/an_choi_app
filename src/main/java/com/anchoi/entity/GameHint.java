package com.anchoi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "game_hint", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"game_detail_id", "level"})
})
public class GameHint extends BaseEntity {

    @Basic
    @Column(name = "game_detail_id")
    private String gameDetailId;

    @Basic
    @Column(name = "level", nullable = false)
    private Integer level;

    @Basic
    @Column(name = "text", columnDefinition = "LONGTEXT")
    private String text;

    @Basic
    @Column(name = "audio_url", columnDefinition = "LONGTEXT")
    private String audioUrl;

    @Basic
    @Column(name = "audio_file_name", length = 255)
    private String audioFileName;

    @Basic
    @Column(name = "point_deduction")
    private Integer pointDeduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_detail_id", insertable = false, updatable = false)
    private GameDetail gameDetail;
}