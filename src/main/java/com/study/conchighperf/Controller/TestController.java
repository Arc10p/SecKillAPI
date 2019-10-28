package com.study.conchighperf.Controller;

import com.study.conchighperf.POJO.Information;
import com.study.conchighperf.RedisService.UserInfoService;
import com.study.conchighperf.Util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("test")
public class TestController {
    @Autowired
    UserInfoService userInfoService;

    @RequestMapping("/login")
    @ResponseBody
    public Information login(HttpServletRequest httpServletRequest){
        Information a=new Information();
        if (httpServletRequest.isRequestedSessionIdValid()){
        a.setInfo(httpServletRequest.getRequestedSessionId());
        }else {
            a.setInfo(httpServletRequest.getSession(true).getId());
            userInfoService.saveSession(a.getInfo(), RandomUtil.getInt());
        }
        return a;
    }
}
