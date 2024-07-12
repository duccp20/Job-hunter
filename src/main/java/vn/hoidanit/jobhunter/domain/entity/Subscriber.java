package vn.hoidanit.jobhunter.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.hoidanit.jobhunter.config.SecurityUtil;

import java.time.Instant;
import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "subcribers")
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    String name;

    @ManyToMany
    @JsonIgnoreProperties(value = {"skills"})
    @JoinTable(name = "subcriber_skill",
            joinColumns = @JoinColumn(name = "subcriber_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))

    List<Skill> skills;

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "updated_by")
    String updatedBy;

    @Column(name = "created_at")
    Instant createdAt;

    @Column(name = "updated_at")
    Instant updatedAt;

    @PrePersist
    public void handleAddBeforePersist() {
        this.createdAt = Instant.now();
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent()
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
    }

    @PreUpdate
    public void handleAddBeforeUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent()
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
    }




}
