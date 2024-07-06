package vn.hoidanit.jobhunter.domain.DTO.Request.job;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.hoidanit.jobhunter.domain.DTO.Request.skill.SkillRequest;
import vn.hoidanit.jobhunter.util.Enum.LevelEnum;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequest {
    String name;
    String location;
    double salary;
    int quantity;
    LevelEnum level;
    @Column(columnDefinition = "mediumtext")
    String description;
    Instant startDate;
    Instant endDate;
    boolean active;

}
