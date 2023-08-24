package com.anchoi.response;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class PointVietnamV1Response {

    private String id;
    private String pointType;
    private String itemId;
    private String parentId;
    private Double x;
    private Double y;
    private String districtId;
    private String name;

    public PointVietnamV1Response(String id, String pointType, String itemId, String parentId, Double x, Double y, String districtId,String name) {
        this.id = id;
        this.pointType = pointType;
        this.itemId = itemId;
        this.parentId = parentId;
        this.x = x;
        this.y = y;
        this.districtId = districtId;
        this.name = name;
    }

    public PointVietnamV1Response() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
