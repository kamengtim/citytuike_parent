package com.citytuike.service;

import com.citytuike.mapper.*;
import com.citytuike.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private TpPhotoAlbumTmpMapper tpPhotoAlbumTmpMapper;
    @Autowired
    private TpPhotoAlbumTmpClassifyMapper tpPhotoAlbumTmpClassifyMapper;
    @Autowired
    private TpPhotoAlbumAdMapper tpPhotoAlbumAdMapper;
    @Autowired
    private TpPhotoAlbumUserMapper tpPhotoAlbumUserMapper;
    @Autowired
    private TpPhotoAlbumUserImageMapper tpPhotoAlbumUserImageMapper;
    @Autowired
    private TpPhotoAlbumCommentMapper tpPhotoAlbumCommentMapper;


    @Override
    public LimitPageList getLimitListByCid(String c_id, Integer p) {
        LimitPageList LimitPageStuList = new LimitPageList();
        int totalCount=tpPhotoAlbumTmpMapper.getCount(c_id);//获取总的记录数
        List<TpUserFriendApply> stuList=new ArrayList<TpUserFriendApply>();
        Page page=null;
        if(p!=null){
            page=new Page(totalCount, p);
            page.setPageSize(10);
            stuList=tpPhotoAlbumTmpMapper.selectByPage(c_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据

        }else{
            page=new Page(totalCount, 1);//初始化pageNow为1
            page.setPageSize(10);
            stuList=tpPhotoAlbumTmpMapper.selectByPage(c_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据

        }
        LimitPageStuList.setPage(page);
        LimitPageStuList.setList(stuList);
        return LimitPageStuList;
    }

    @Override
    public List<TpPhotoAlbumTmpClassify> findAllClassify() {
        return tpPhotoAlbumTmpClassifyMapper.findAllClassify();
    }

    @Override
    public int insertPhotoAlbumAd(TpPhotoAlbumAd tpPhotoAlbumAd) {
        return tpPhotoAlbumAdMapper.insert(tpPhotoAlbumAd);
    }

    @Override
    public TpPhotoAlbumAd findOneAlbumAdById(String ad_id) {
        return tpPhotoAlbumAdMapper.selectByPrimaryKey(Integer.parseInt(ad_id));
    }

    @Override
    public int updataPhotoAlbumAd(TpPhotoAlbumAd tpPhotoAlbumAd) {
        return tpPhotoAlbumAdMapper.updateByPrimaryKeySelective(tpPhotoAlbumAd);
    }

    @Override
    public TpPhotoAlbumUser findOneAlbumUserById(String p_id) {
        return tpPhotoAlbumUserMapper.selectByPrimaryKey(Integer.parseInt(p_id));
    }

    @Override
    public int
    updataPhotoAlbumUser(TpPhotoAlbumUser tpPhotoAlbumUser) {
        return tpPhotoAlbumUserMapper.updateByPrimaryKeySelective(tpPhotoAlbumUser);
    }

    @Override
    public int insertPhotoAlbumUser(TpPhotoAlbumUser tpPhotoAlbumUser) {
        return tpPhotoAlbumUserMapper.insert(tpPhotoAlbumUser);
    }

    @Override
    public int insertPHotoAlbumUserImage(TpPhotoAlbumUserImage tpPhotoAlbumUserImage) {
        return tpPhotoAlbumUserImageMapper.insert(tpPhotoAlbumUserImage);
    }

    @Override
    public int deleteUserImageByPid(String p_id) {
        return tpPhotoAlbumUserImageMapper.deleteByPid(p_id);
    }

    @Override
    public LimitPageList getlimitListPhotoAlbumUser(Integer p, Integer user_id) {
        LimitPageList LimitPageStuList = new LimitPageList();
        int totalCount=tpPhotoAlbumUserMapper.getCount(user_id);//获取总的记录数
        List<TpUserFriendApply> stuList=new ArrayList<TpUserFriendApply>();
        Page page=null;
        if(p!=null){
            page=new Page(totalCount, p);
            page.setPageSize(10);
            stuList=tpPhotoAlbumUserMapper.selectByPage(user_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据

        }else{
            page=new Page(totalCount, 1);//初始化pageNow为1
            page.setPageSize(10);
            stuList=tpPhotoAlbumUserMapper.selectByPage(user_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据

        }
        LimitPageStuList.setPage(page);
        LimitPageStuList.setList(stuList);
        return LimitPageStuList;
    }

    @Override
    public List<TpPhotoAlbumUserImage> findAllPhotoAlbumUserImageByPid(Integer pId) {
        return tpPhotoAlbumUserImageMapper.findAllPhotoAlbumUserImageByPid(pId);
    }

    @Override
    public TpPhotoAlbumTmp findOneAlbumTmpById(Integer tmpId) {
        return tpPhotoAlbumTmpMapper.selectByPrimaryKey(tmpId);
    }

    @Override
    public int deletePhotoAlbumUser(String p_id) {
        return tpPhotoAlbumUserMapper.deleteByPrimaryKey(Integer.parseInt(p_id));
    }

    @Override
    public int updataPhotoAlbumUserPv(String p_id) {
        return tpPhotoAlbumUserMapper.updataPhotoAlbumUserPv(p_id);
    }

    @Override
    public int updataPhotoAlbumUserShare(String p_id) {
        return tpPhotoAlbumUserMapper.updataPhotoAlbumUserShare(p_id);
    }

    @Override
    public int insertPhotoAlbumComment(TpPhotoAlbumComment tpPhotoAlbumComment) {
        return tpPhotoAlbumCommentMapper.insert(tpPhotoAlbumComment);
    }

    @Override
    public List<TpPhotoAlbumComment> findAllPhotoAlbumComment(String p_id) {
        return tpPhotoAlbumCommentMapper.findAllPhotoAlbumComment(p_id);
    }
}
