package vn.hoidanit.jobhunter.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.hoidanit.jobhunter.domain.DTO.Response.email.SendMailSubscriberJob;
import vn.hoidanit.jobhunter.domain.entity.Job;

@Mapper(componentModel = "spring")
public interface MailMapper {


    @Mapping(target = "skills", source = "job.skills")
    @Mapping(target = "name", source = "job.name")
    @Mapping(target = "location", source = "job.location")
    @Mapping(target = "salary", source = "job.salary")
    SendMailSubscriberJob toSendMailSubscriberJob(Job job);
}
