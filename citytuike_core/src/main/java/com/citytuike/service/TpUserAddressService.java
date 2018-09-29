package com.citytuike.service;

import com.citytuike.model.TpUserAddress;

public interface TpUserAddressService {
    TpUserAddress getAddress(String address_id, Integer user_id);
}
