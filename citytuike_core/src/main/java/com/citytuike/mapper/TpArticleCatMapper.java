package com.citytuike.mapper;

import com.citytuike.model.TpArticleCat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TpArticleCatMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_article_cat
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer catId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_article_cat
     *
     * @mbggenerated
     */
    int insert(TpArticleCat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_article_cat
     *
     * @mbggenerated
     */
    int insertSelective(TpArticleCat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_article_cat
     *
     * @mbggenerated
     */
    TpArticleCat selectByPrimaryKey(Integer catId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_article_cat
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TpArticleCat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_article_cat
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TpArticleCat record);

    List<TpArticleCat> findArticleCatByPid(@Param("pid") Integer pid);

    int getClick_Count(@Param("catId") Integer catId);
}