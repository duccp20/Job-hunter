package vn.hoidanit.jobhunter.service;

import vn.hoidanit.jobhunter.domain.DTO.Request.subscriber.SubscriberRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.subscriber.SubscriberUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.subscriber.SubscriberResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.subscriber.SubscriberUpdateResponse;
import vn.hoidanit.jobhunter.domain.entity.Subscriber;

import java.util.List;

public interface SubscriberService {
    SubscriberResponse handleCreateSubscriber(SubscriberRequest subscriberRequest);

    SubscriberUpdateResponse handleUpdateSubscriber(SubscriberUpdateRequest subscriberRequest);

    List<Subscriber> handleFindAll();

    SubscriberResponse handleGetSubscriberSkillsByEmail(String email);
}
