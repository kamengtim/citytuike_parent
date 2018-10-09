package com.citytuike.service;

import com.citytuike.model.*;

import java.util.List;

public interface PhotoService {

    LimitPageList getLimitListByCid(String c_id, Integer page);

    List<TpPhotoAlbumTmpClassify> findAllClassify();

    int insertPhotoAlbumAd(TpPhotoAlbumAd tpPhotoAlbumAd);

    TpPhotoAlbumAd findOneAlbumAdById(String ad_id);

    int updataPhotoAlbumAd(TpPhotoAlbumAd tpPhotoAlbumAd);

    TpPhotoAlbumUser findOneAlbumUserById(String p_id);

    int updataPhotoAlbumUser(TpPhotoAlbumUser tpPhotoAlbumUser);

    int insertPhotoAlbumUser(TpPhotoAlbumUser tpPhotoAlbumUser);

    int insertPHotoAlbumUserImage(TpPhotoAlbumUserImage tpPhotoAlbumUserImage);

    int deleteUserImageByPid(String p_id);

    LimitPageList getlimitListPhotoAlbumUser(Integer page, Integer user_id);

    List<TpPhotoAlbumUserImage> findAllPhotoAlbumUserImageByPid(Integer pId);
}
