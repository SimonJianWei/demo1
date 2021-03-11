package com.example.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class VehicleinfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getVehicleinfoList() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" top 1000 * ");
        sb.append(" FROM  vehicleinfo vcl_info");
        sb.append("  LEFT JOIN CTY_VCL.dbo.Tmnl_Install_Last tmnl_install ON vcl_info.vehicleid = tmnl_install.TmnlIL_Vcl_ID");
        return jdbcTemplate.queryForList(sb.toString());
    }



    public static void main(String[] args) {
        String[] words = new String[]{"Hello","World"};
        List<String[]> a = Arrays.stream(words)
                .map(word -> word.split(""))
                .distinct()
                .collect(Collectors.toList());
       a.forEach(System.out::print);


    }
}
