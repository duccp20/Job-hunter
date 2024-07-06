package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.hoidanit.jobhunter.domain.entity.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {

    Optional<Skill> findSkillByName(String name);

    boolean existsByName(String name);

    List<Skill> findByIdIn (List<Long> id);

    Optional<Skill> findSkillById(Long id);
}
