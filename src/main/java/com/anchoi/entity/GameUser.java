package com.anchoi.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "GAME_USER")
@Data
public class GameUser {
    @Id
    private String id;
    private String userId;
    private Long timeCount;
    private String gameId;
    private Date createdDate;
}
