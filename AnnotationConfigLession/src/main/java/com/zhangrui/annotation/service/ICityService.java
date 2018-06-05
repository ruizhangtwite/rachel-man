package com.zhangrui.annotation.service;

import com.zhangrui.annotation.entity.City;

import java.util.List;


public interface ICityService {

    public List<City> getCityAll();

    public City getCityByID(Integer id);
}
