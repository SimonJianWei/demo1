package com.example.service;

import com.example.dao.TmnlInfo;
import com.example.dao.VehicleinfoDao;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class VehicleinfoService {

    @Autowired
    private VehicleinfoDao vehicleinfodao;
    @Autowired
    private TmnlInfo tmnlInfo;




    public void praticeLambda(String program) {
        List<Map<String, Object>> dataList = vehicleinfodao.getVehicleinfoList();
        System.out.println(program);
//        List<Map<String, Object>>distinctList=dataList.stream().distinct().collect(Collectors.toList());//去重
//        List<Map<String, Object>> distinctList = dataList.stream().filter(data -> {
//            if ("红色".equals(MapUtils.getString(data, "af_color"))) {
//                return true;
//            }
//            return false;
//        }).collect(Collectors.toList());
//        List<Map<String, Object>> distinctList =
        dataList.stream().filter(e->{
            String time=MapUtils.getString(e,"AccountBuildTime");
            SimpleDateFormat sd=new SimpleDateFormat("yyyy-mm-dd");
            try {
                Date timezone=sd.parse(time);
                Date compare=sd.parse("2099-13-31");
                return  timezone.before(compare)&&MapUtils.getIntValue(e,"AccountID")>100;
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            return false;
        }).map(e->{String name=e.get("Account")+"";
        return name;}).forEach( System.out::println);
//        System.out.println(distinctList);

    }
    public void  research(){
        List<Map<String, Object>> dataList = vehicleinfodao.getVehicleinfoList();
        List<Map<String, Object>> tmnlinfoList = tmnlInfo.getTmnlinfoList();
        List<Map<String,Object>> collectlist=new ArrayList<>();
        Stream<Map<String, Object>> stream1 = dataList.stream();
        Stream<Map<String, Object>> stream2 = tmnlinfoList.stream();
        //多流合并
        Stream.concat(stream1,stream2).map((m1)->{
            return m1.get("vehicleid");
        }).collect(Collectors.toList());
    }


}
