package vn.hoidanit.jobhunter.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.hoidanit.jobhunter.domain.DTO.Request.subscriber.SubscriberRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.subscriber.SubscriberUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.subscriber.SubscriberResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.subscriber.SubscriberUpdateResponse;
import vn.hoidanit.jobhunter.domain.entity.Subscriber;

@Mapper(componentModel = "spring")
public interface SubscriberMapper {
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Subscriber toSubscriber(SubscriberRequest subscriberRequest);

    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "email", ignore = true)
    void updateSubscriber(@MappingTarget Subscriber subscriber, SubscriberUpdateRequest subscriberRequest);


    SubscriberResponse toSubscriberResponse(Subscriber subscriber);

    SubscriberUpdateResponse toUpdateSubscriberResponse(Subscriber subscriber);
}
