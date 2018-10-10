package com.citytuike.model;

import java.io.Serializable;
import java.util.Date;

public class TpPhotoAlbumComment implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_photo_album_comment.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_photo_album_comment.p_id
     *
     * @mbggenerated
     */
    private Integer pId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_photo_album_comment.user_id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_photo_album_comment.add_time
     *
     * @mbggenerated
     */
    private Date addTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_photo_album_comment.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_photo_album_comment.status
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_photo_album_comment.id
     *
     * @return the value of tp_photo_album_comment.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_photo_album_comment.id
     *
     * @param id the value for tp_photo_album_comment.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_photo_album_comment.p_id
     *
     * @return the value of tp_photo_album_comment.p_id
     *
     * @mbggenerated
     */
    public Integer getpId() {
        return pId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_photo_album_comment.p_id
     *
     * @param pId the value for tp_photo_album_comment.p_id
     *
     * @mbggenerated
     */
    public void setpId(Integer pId) {
        this.pId = pId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_photo_album_comment.user_id
     *
     * @return the value of tp_photo_album_comment.user_id
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_photo_album_comment.user_id
     *
     * @param userId the value for tp_photo_album_comment.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_photo_album_comment.add_time
     *
     * @return the value of tp_photo_album_comment.add_time
     *
     * @mbggenerated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_photo_album_comment.add_time
     *
     * @param addTime the value for tp_photo_album_comment.add_time
     *
     * @mbggenerated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_photo_album_comment.content
     *
     * @return the value of tp_photo_album_comment.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_photo_album_comment.content
     *
     * @param content the value for tp_photo_album_comment.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_photo_album_comment.status
     *
     * @return the value of tp_photo_album_comment.status
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_photo_album_comment.status
     *
     * @param status the value for tp_photo_album_comment.status
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}