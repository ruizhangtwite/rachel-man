package com.zhangrui.business.world.service.impl;

import com.zhangrui.business.world.domain.City;
import com.zhangrui.business.world.mapper.CityMapper;
import com.zhangrui.business.world.service.ICityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nsc on 2018/4/3.
 */
@Service
public class CityServiceImpl implements ICityService {

    Logger logger = Logger.getLogger(CityServiceImpl.class);


    @Autowired
    private CityMapper cityMapper;

    @Override
    @Cacheable(cacheNames = "citys")
    public List<City> getAllCitys() {
        System.out.println("出现这个说明没有进入缓存中....");
        return cityMapper.getAllCity();
    }
}
