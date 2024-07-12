package vn.hoidanit.jobhunter.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.DTO.Request.skill.SkillRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.subscriber.SubscriberRequest;
import vn.hoidanit.jobhunter.domain.DTO.Request.subscriber.SubscriberUpdateRequest;
import vn.hoidanit.jobhunter.domain.DTO.Response.subscriber.SubscriberResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.subscriber.SubscriberUpdateResponse;
import vn.hoidanit.jobhunter.domain.entity.Skill;
import vn.hoidanit.jobhunter.domain.entity.Subscriber;
import vn.hoidanit.jobhunter.domain.mapper.SubscriberMapper;
import vn.hoidanit.jobhunter.exception.AppException;
import vn.hoidanit.jobhunter.repository.SkillRepository;
import vn.hoidanit.jobhunter.repository.SubscriberRepository;
import vn.hoidanit.jobhunter.service.SubscriberService;
import vn.hoidanit.jobhunter.util.Enum.ErrorCode;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberMapper subscriberMapper;
    private final SkillRepository skillRepository;
    private final SubscriberRepository subscriberRepository;

    @Override
    public SubscriberResponse handleCreateSubscriber(SubscriberRequest subscriberRequest) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!email.equals(subscriberRequest.getEmail())) {
            throw new AppException(ErrorCode.INVALID_EMAIL);
        }

        List<Long> skillIds = new ArrayList<>();
        for (SkillRequest skillRequest : subscriberRequest.getSkills()) {
            skillIds.add(skillRequest.getId());
        }

        List<Skill> availableSkills = skillRepository.findByIdIn(skillIds);

        Subscriber subscriber = subscriberMapper.toSubscriber(subscriberRequest);
        subscriber.setSkills(availableSkills);

        return subscriberMapper.toSubscriberResponse(subscriberRepository.save(subscriber));
    }

    @Override
    public SubscriberUpdateResponse handleUpdateSubscriber(SubscriberUpdateRequest subscriberRequest) {

        Subscriber subscriber = subscriberRepository.findById(subscriberRequest.getId()).orElseThrow(
                () -> new AppException(ErrorCode.SUBSCRIBER_NOT_FOUND)
        );

        List<Long> skillIds = new ArrayList<>();
        for (SkillRequest skillRequest : subscriberRequest.getSkills()) {
            skillIds.add(skillRequest.getId());
        }

        List<Skill> availableSkills = skillRepository.findByIdIn(skillIds);
        subscriberMapper.updateSubscriber(subscriber, subscriberRequest);
        subscriber.setSkills(availableSkills);

        return subscriberMapper.toUpdateSubscriberResponse(subscriberRepository.save(subscriber));
    }

    @Override
    public List<Subscriber> handleFindAll() {
        return subscriberRepository.findAll();
    }

    @Override
    public SubscriberResponse handleGetSubscriberSkillsByEmail(String email) {

        Subscriber subscriber = subscriberRepository.findByEmail(email);
        if (subscriber != null) {
            return subscriberMapper.toSubscriberResponse(subscriber);
        } else {
            throw new AppException(ErrorCode.SUBSCRIBER_NOT_FOUND);
        }
    }
}
