package com.citytuike.service;

import com.citytuike.mapper.TpUserFriendApplyMapper;
import com.citytuike.mapper.TpUserFriendsMapper;
import com.citytuike.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private TpUserFriendsMapper tpUserFriendsMapper;
    @Autowired
    private TpUserFriendApplyMapper tpUserFriendApplyMapper;

    @Override
    public LimitPageList getLimitListByApply(Integer user_id, Integer p, Integer size) {
        LimitPageList LimitPageStuList = new LimitPageList();
        int totalCount=tpUserFriendApplyMapper.getCount(user_id);//获取总的记录数
        List<TpUserFriendApply> stuList=new ArrayList<TpUserFriendApply>();
        Page page=null;
        if(p!=null){
            page=new Page(totalCount, p);
            page.setPageSize(size);
            stuList=tpUserFriendApplyMapper.selectByPage(user_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据

        }else{
            page=new Page(totalCount, 1);//初始化pageNow为1
            page.setPageSize(size);
            stuList=tpUserFriendApplyMapper.selectByPage(user_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据

        }
        LimitPageStuList.setPage(page);
        LimitPageStuList.setList(stuList);
        return LimitPageStuList;
    }

    @Override
    public int insertUserFriendsApply(TpUserFriendApply tpUserFriendApply) {
        return tpUserFriendApplyMapper.insert(tpUserFriendApply);
    }

    @Override
    public TpUserFriendApply findOneUserFriendApplyById(Integer id) {
        return tpUserFriendApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updataApplyFriends(TpUserFriendApply tpUserFriendApply1) {
        return tpUserFriendApplyMapper.updateByPrimaryKeySelective(tpUserFriendApply1);
    }

    @Override
    public int insertUserFriends(TpUserFriends tpUserFriends) {
        return tpUserFriendsMapper.insert(tpUserFriends);
    }

    @Override
    public LimitPageList getLimitListByUserFriends(Integer p, Integer size, Integer user_id, String keyword) {
        LimitPageList LimitPageStuList = new LimitPageList();
        int totalCount=tpUserFriendsMapper.getCount(keyword, user_id);//获取总的记录数
        List<TpUserFriends> stuList=new ArrayList<TpUserFriends>();
        Page page=null;
        if(p!=null){
            page=new Page(totalCount, p);
            page.setPageSize(size);
            stuList=tpUserFriendsMapper.selectByPage(keyword, user_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据

        }else{
            page=new Page(totalCount, 1);//初始化pageNow为1
            page.setPageSize(size);
            stuList=tpUserFriendsMapper.selectByPage(keyword, user_id, page.getStartPos(), page.getPageSize());//从startPos开始，获取pageSize条数据

        }
        LimitPageStuList.setPage(page);
        LimitPageStuList.setList(stuList);
        return LimitPageStuList;
    }
}
