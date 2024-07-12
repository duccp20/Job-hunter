package vn.hoidanit.jobhunter.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    public void run() {
        System.out.println(">>> ScheduleTask run");
    }

}
