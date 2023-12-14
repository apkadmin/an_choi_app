package com.anchoi.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "post")
public class Post  extends BaseEntity{
    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
  List<PostI18n> postI18nList;


}
