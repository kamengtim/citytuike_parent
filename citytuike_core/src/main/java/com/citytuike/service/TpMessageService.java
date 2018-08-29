package com.citytuike.service;

import com.citytuike.model.LimitPageList;

public interface TpMessageService {
    LimitPageList getLimitPageList(Integer user_id, String page, String cate);
}
