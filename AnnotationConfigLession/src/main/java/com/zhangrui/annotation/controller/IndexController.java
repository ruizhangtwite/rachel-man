package com.zhangrui.annotation.controller;

import com.zhangrui.annotation.entity.City;
import com.zhangrui.annotation.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ICityService service;

    @RequestMapping("/home")
    public String index(){
        return "index";
    }


    @RequestMapping("/city")
    @ResponseBody
    public List<City> getAllCity(){
        return service.getCityAll();
    }


    @RequestMapping(value = "/getcity/{id}"/*, produces = MediaType.APPLICATION_JSON_VALUE*/)
    @ResponseBody
    public City getCity(@PathVariable Integer id){
        return service.getCityByID(id);
    }
}
