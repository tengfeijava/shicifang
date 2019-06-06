package com.tensquare.smsmq.listenner;

import com.aliyuncs.exceptions.ClientException;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "smsmq") //监听的队列   （直列模式）
public class SmsLinstenner {

    //读取配置中的ak
//    @Value("${aliyun.sms.template_code}")
//    private String temlateCode;
//    @Value("${aliyun.sms.sign_name}")
//    private String signName;


    @RabbitHandler   //接受发送的 Map 消息
    public void sendSms(Map<String,String> map){
        System.out.println("手机号："+map.get("cellphone"));
        System.out.println("验证码："+map.get("checkcode"));
//        SmsUtil smsUtil = new SmsUtil();
//        try {                      //手机号        摸版号        签名                                  动态验证
//            smsUtil.sendSms(map.get("cellphone"),temlateCode,signName,"{\"number\":\""+map.get("checkcode")+"\"}");
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
    }
}
