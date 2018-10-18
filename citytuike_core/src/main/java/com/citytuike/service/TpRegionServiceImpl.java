package com.citytuike.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.citytuike.mapper.TpRegionMapper;
import com.citytuike.model.TpRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TpRegionServiceImpl implements TpRegionService {
    @Autowired
    private TpRegionMapper tpRegionMapper;
    @Override
    public String getCityName(Integer city) {
        String cityName = tpRegionMapper.getCityName(city);
        return cityName;
    }

    @Override
    public String getProvince(Integer province) {
        String provinceNmae = tpRegionMapper.getProvince(province);
        return provinceNmae;
    }

    @Override
    public String getDistrict(Integer district) {
        String districtName = tpRegionMapper.getDistrict(district);
        return districtName;
    }

    @Override
    public TpRegion getNameByFanId(Integer address) {
        TpRegion tpRegion = tpRegionMapper.getNameByFanId(address);
        return tpRegion;
    }

    @Override
    public String getTwon(Integer twonName) {
        String twon = tpRegionMapper.getTwon(twonName);
        return twon;
    }

    @Override
    public List<TpRegion> selectOne() {
        return tpRegionMapper.selectOne();
    }

    @Override
    public JSONObject selectById(TpRegion tpRegion) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",tpRegion.getId());
        jsonObject.put("name",tpRegion.getName());
        jsonObject.put("initials",tpRegion.getInitials());
        jsonObject.put("level",tpRegion.getLevel());
        jsonObject.put("num",tpRegion.getNum());
        jsonObject.put("parent_id",tpRegion.getParent_id());
        return jsonObject;
    }

    @Override
    public List<TpRegion> selectByParentId(Integer id) {

        /*List<String> list = new ArrayList<String>();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();*/
        List<TpRegion> tpRegions = tpRegionMapper.selectByParentId(id);
        return tpRegions;
        /*for (TpRegion tpRegion : tpRegions) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", tpRegion.getId());
            jsonObject.put("name", tpRegion.getName());
            jsonObject.put("initials", tpRegion.getInitials());
            jsonObject.put("level", tpRegion.getLevel());
            jsonObject.put("num", tpRegion.getNum());
            jsonObject.put("parent_id", tpRegion.getParent_id());
            jsonArray.add(jsonObject);
            list.add((String)jsonObject.get("initials"));
            for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
                for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                    if  (list.get(j).equals(list.get(i)))  {
                        list.remove(j);
                    }
                }
            }
        }
        Collections.sort(list);
        String[] output = list.toArray(new String[0]);
        Iterator<Object> it = jsonArray.iterator();
        while (it.hasNext()) {
            JSONObject ob = (JSONObject) it.next();
            for (int i = 0; i <output.length ; i++) {
                if(ob.getString("initials").equals(output[i])){
                    JSONArray arr = new JSONArray();
                    arr.add(ob);
                    jsonObj.put(ob.getString("initials"),arr);
                }
            }
        }
        return jsonObj;*/
    }

    @Override
    public List<TpRegion> getDis(Integer id) {

        return tpRegionMapper.getDis(id);
    }

}
