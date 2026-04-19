package com.anchoi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "game")
public class Game extends BaseEntity {

    @Basic
    @Column(name = "image", columnDefinition = "LONGTEXT")
    private String image;

    @Basic
    @Column(name = "type")
    private Integer type;

    @Basic
    @Column(name = "name", length = 255)
    private String name;

    @Basic
    @Column(name = "max_diamonds_allowed")
    private Integer maxDiamondsAllowed;

    @Basic
    @Column(name = "enable_hints")
    private Boolean enableHints;

    @Basic
    @Column(name = "hint_config", columnDefinition = "JSON")
    private String hintConfig;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameDetail> dataDetails;
}