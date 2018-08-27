package com.citytuike.service;

import com.citytuike.mapper.TpMessageMapper;
import com.citytuike.mapper.TpUserMessageMapper;
import com.citytuike.model.LimitPageList;
import com.citytuike.model.Page;
import com.citytuike.model.TpMessage;
import com.citytuike.model.TpUserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service@Transactional
public class TpMessageServiceImpl implements TpMessageService {
    @Autowired
    private TpMessageMapper tpMessageMapper;
    @Autowired
    private TpUserMessageMapper tpUserMessageMapper;
    public LimitPageList getLimitPageList(Integer user_id, String page, String cate) {
        List<TpUserMessage> tpUserMessages = tpUserMessageMapper.selectMessageByUser(user_id);
         LimitPageList limitPageList = new LimitPageList();
        for (TpUserMessage tpUserMessage : tpUserMessages) {
            List<TpMessage> tpMessages = tpMessageMapper.selectMessageByMessageId(tpUserMessage.getMessage_id());
            for (TpMessage tpMessage : tpMessages) {
                int totalCount = tpMessageMapper.selectCount(tpMessage.getMessage_id());
                List<TpMessage> stuList = new ArrayList<TpMessage>();
                Page PageSize = null;
                if(page != null){
                    PageSize=new Page(totalCount,Integer.valueOf(page));
                    PageSize.setPageSize(10);
                    stuList = tpMessageMapper.selectByPage(PageSize.getStartPos(),PageSize.getPageSize(),tpMessage.getMessage_id(),cate);
                }else{
                    PageSize = new Page(totalCount,1);
                    PageSize.setPageSize(10);
                    stuList = tpMessageMapper.selectByPage(PageSize.getStartPos(),PageSize.getPageSize(),tpMessage.getMessage_id(),cate);
                }
                limitPageList.setPage(PageSize);
                limitPageList.setList(stuList);
            }
        }
                return limitPageList;
    }
}
