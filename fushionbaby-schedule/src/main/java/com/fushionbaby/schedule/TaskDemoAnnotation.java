package com.fushionbaby.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("taskDemoAnnotation")
public class TaskDemoAnnotation {
	
	private static boolean runFlag = false;

	@Scheduled(cron="0 0/1 * * * ?")
	public void excute() {  
		if(runFlag) {
			System.out.println("正在运行"); 
			return;
		}
		runFlag = true;
	    System.out.println("..........注解形式..................."+System.currentTimeMillis()); 
	    runFlag = false;
	} 
}
