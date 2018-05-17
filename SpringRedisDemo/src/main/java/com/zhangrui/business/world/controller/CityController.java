package com.zhangrui.business.world.controller;

import com.zhangrui.business.world.domain.City;
import com.zhangrui.business.world.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by nsc on 2018/4/3.
 */
@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private ICityService cityService;

    @RequestMapping("/all")
    @ResponseBody
    public List<City> getAllCity(){
        return cityService.getAllCitys();
    }
}
