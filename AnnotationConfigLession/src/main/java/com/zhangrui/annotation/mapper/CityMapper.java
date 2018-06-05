package com.zhangrui.annotation.mapper;


import com.zhangrui.annotation.config.BaseMapperInterface;
import com.zhangrui.annotation.entity.City;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;


public interface CityMapper extends BaseMapperInterface{

    @Results(value = {
            @Result(column = "ID", property = "id", id = true),
            @Result(column = "Name", property = "name"),
            @Result(column = "CountryCode", property = "counntryCode"),
            @Result(column = "District", property = "district"),
            @Result(column = "Population", property = "population")
    })
    @Select("select * from city limit 0,10")
    public List<City> getAllCity();


    @Results(value = {
            @Result(column = "ID", property = "id", id = true),
            @Result(column = "Name", property = "name"),
            @Result(column = "CountryCode", property = "counntryCode"),
            @Result(column = "District", property = "district"),
            @Result(column = "Population", property = "population")
    })
    @Select("select * from city where id = #{id}")
    public City getCityByID(Integer id);
}
