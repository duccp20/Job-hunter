package vn.hoidanit.jobhunter.service;

public interface EmailService {
    void sendMailWithTemplate(String to, String subject, String templateName, String userName, Object value);
}
