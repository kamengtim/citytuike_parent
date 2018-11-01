package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpChatFriendList;
import com.citytuike.model.TpChatGroupUser;
import com.citytuike.model.TpUsers;

public interface ChatService {

    LimitPageList getLimitPageListByChatList(String im_id, Integer type, Integer page);

    JSONObject getChatFriendListJson(TpChatFriendList tpChatFriendList, TpUsers tpUsers);

    LimitPageList getLImitPageLIstByChatLogList(String room, Integer page);

    int updataChatFriendListIsRead(String room, String im_ids);

    int insertChatGroupUser(TpChatGroupUser tpChatGroupUser);

    int updateChatGroupUserByStatus(String group_id, String user_id);
}
