package com.zhangrui.annotation.service.impl;

import com.zhangrui.annotation.entity.City;
import com.zhangrui.annotation.mapper.CityMapper;
import com.zhangrui.annotation.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements ICityService {

    @Autowired
    private CityMapper mapper;


    @Override
    public List<City> getCityAll() {
        return mapper.getAllCity();
    }

    @Override
    public City getCityByID(Integer id) {
        return mapper.getCityByID(id);
    }
}
