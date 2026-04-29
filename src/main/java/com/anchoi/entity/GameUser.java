package com.anchoi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "GAME_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameUser {
    @Id
    private String id;
    
    private String userId;
    
    private String username;
    
    private Long timeCount;
    
    private String gameId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
}
