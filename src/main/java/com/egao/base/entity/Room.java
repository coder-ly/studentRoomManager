package com.egao.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

/**
 * 宿舍信息
 * Created by cy on 2020-05-06 01:33:22
 */
@TableName("tb_room")
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 宿舍编号
     */
    private String roomNo;

    /**
     * 公寓楼号
     */
    private String shno;

    /**
     * 宿舍门牌号
     */
    private String spno;

    /**
     * 备注
     */
    private String comments;

    /**
     * 是否删除,0否,1是
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 可住几人
     */
    private String sperno;

    /**
     * 状态 0：未满员 1：已满员
     */
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getShno() {
        return shno;
    }

    public void setShno(String shno) {
        this.shno = shno;
    }

    public String getSpno() {
        return spno;
    }

    public void setSpno(String spno) {
        this.spno = spno;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSperno() {
        return sperno;
    }

    public void setSperno(String sperno) {
        this.sperno = sperno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Room{" +
                ", id=" + id +
                ", roomNo=" + roomNo +
                ", shno=" + shno +
                ", spno=" + spno +
                ", comments=" + comments +
                ", deleted=" + deleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", sperno=" + sperno +
                ", status=" + status +
                "}";
    }

}
