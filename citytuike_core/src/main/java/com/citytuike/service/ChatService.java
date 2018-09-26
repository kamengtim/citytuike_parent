package com.citytuike.service;

import com.alibaba.fastjson.JSONObject;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.TpChatFriendList;
import com.citytuike.model.TpUsers;

public interface ChatService {

    LimitPageList getLimitPageListByChatList(String im_id, Integer page);

    JSONObject getChatFriendListJson(TpChatFriendList tpChatFriendList, TpUsers tpUsers);

    LimitPageList getLImitPageLIstByChatLogList(String im_id, Integer page);
}
