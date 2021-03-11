package com.example.contorller;

import com.example.service.MqSendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RabbitTes
 * @Author Simon
 * @Date 2021/1/6 10:52
 * @Description
 */


@RestController
public class RabbitTes {
    @Autowired
    private MqSendMessage sendMessage;

    @GetMapping(path="/test/{id}")
    public void  senTopic(@PathVariable(name = "id")String id){
        System.out.println("第"+id+"消息");
        Map map=new HashMap<String, Object>();
        map.put("name", "simon");
        map.put("addr", "shijiiazhuang");
        try {
            sendMessage.sendTopic(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/test/fanout")
    public void sendFanout(HttpServletRequest request, HttpServletResponse response){
        try {
            sendMessage.sendDelay();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
