package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.hoidanit.jobhunter.domain.DTO.Response.subscriber.SubscriberResponse;
import vn.hoidanit.jobhunter.domain.DTO.Response.subscriber.SubscriberUpdateResponse;
import vn.hoidanit.jobhunter.domain.entity.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long>, JpaSpecificationExecutor<Subscriber> {

    Subscriber findByEmail(String email);
}
