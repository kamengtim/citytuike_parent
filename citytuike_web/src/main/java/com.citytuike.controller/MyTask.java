package com.citytuike.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTask {
	
  @Scheduled(cron = "0 10 18 * * ?")//每分钟都执行
  public void execute(){
	    System.out.println("基于注解配置的spring定时任务！111");
	  }
}
