package com.citytuike.service;

import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpUserFriendApply;
import com.citytuike.model.TpUserFriends;

public interface FriendService {

    LimitPageList getLimitListByApply(Integer user_id, Integer p, Integer size);

    int insertUserFriendsApply(TpUserFriendApply tpUserFriendApply);

    TpUserFriendApply findOneUserFriendApplyById(Integer id);

    int updataApplyFriends(TpUserFriendApply tpUserFriendApply1);

    int insertUserFriends(TpUserFriends tpUserFriends);

    LimitPageList getLimitListByUserFriends(Integer p, Integer size, Integer user_id, String keyword);
}
