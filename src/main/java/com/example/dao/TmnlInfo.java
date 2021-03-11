package com.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: chenjianwei
 * @Date: 2020/10/10/11:30
 * @Description:
 */
@Component
public class TmnlInfo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getTmnlinfoList() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" top 1000 * ");
        sb.append(" FROM  CTY_VCL.dbo.tmnl_info");
        sb.append("  INNER JOIN CTY_VCL.dbo.Tmnl_Install_Last ON Tmnl_ID = TmnlIL_Tmnl_ID");
        return jdbcTemplate.queryForList(sb.toString());
    }

}
