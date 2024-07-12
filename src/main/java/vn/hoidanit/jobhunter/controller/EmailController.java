package vn.hoidanit.jobhunter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.jobhunter.domain.DTO.Response.email.SendMailSubscriberJob;
import vn.hoidanit.jobhunter.domain.entity.Job;
import vn.hoidanit.jobhunter.domain.entity.Skill;
import vn.hoidanit.jobhunter.domain.entity.Subscriber;
import vn.hoidanit.jobhunter.domain.mapper.MailMapper;
import vn.hoidanit.jobhunter.repository.JobRepository;
import vn.hoidanit.jobhunter.service.EmailService;
import vn.hoidanit.jobhunter.service.JobService;
import vn.hoidanit.jobhunter.service.SubscriberService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/email")
public class EmailController {

    private final EmailService emailService;

    private final SubscriberService subscriberService;

    private final JobService jobService;
    private final JobRepository jobRepository;

    private final MailMapper mailMapper;


    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void getEmail() {

        System.out.println(">>> getEmail");
        // Retrieve all subscribers
        List<Subscriber> subscribers = subscriberService.handleFindAll();

        // Process each subscriber
        for (Subscriber subscriber : subscribers) {
            List<Skill> skills = subscriber.getSkills();

            if (!skills.isEmpty()) {
                // Find jobs based on subscriber's skills
                List<Job> matchingJobs = jobRepository.findBySkillsIn(skills);

                List<SendMailSubscriberJob> sendMailJobs = new ArrayList<>();

                // Map jobs to email format
                for (Job job : matchingJobs) {
                    sendMailJobs.add(mailMapper.toSendMailSubscriberJob(job));
                }

                // Send email to subscriber
                emailService.sendMailWithTemplate(
                        subscriber.getEmail(),
                        "Matching Jobs For You today!",
                        "job",
                        subscriber.getName(),
                        sendMailJobs
                );
            }
        }

    }
}
