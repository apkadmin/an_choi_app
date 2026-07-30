package com.anchoi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "game_detail")
public class GameDetail extends BaseEntity {

    @Basic
    @Column(name = "game_id")
    private String gameId;

    @Basic
    @Column(name = "x")
    private Double x;

    @Basic
    @Column(name = "y")
    private Double y;

    @Basic
    @Column(name = "width")
    private Double width;

    @Basic
    @Column(name = "height")
    private Double height;

    @Basic
    @Column(name = "d", columnDefinition = "LONGTEXT")
    private String d;

    @Basic
    @Column(name = "color")
    private String color;

    @Basic
    @Column(name = "max_diamonds")
    private Integer maxDiamonds;

    @OneToMany(mappedBy = "gameDetail", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameHint> hints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", insertable = false, updatable = false)
    private Game game;
    @Basic
    @Column(name = "transform")
    private String transform;

    @Basic
    @Column(name = "type")
    private String type;
}