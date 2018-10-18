package com.citytuike.service;

public interface TpScanHelpService {
    int fansHelp(Integer user_id);

    int toDayHelp(Integer invite_id);

    int insert(Integer user_id, Integer invite_id);
}
