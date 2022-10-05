package edu.schoo21.openprojectback.repository;

import edu.schoo21.openprojectback.models.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
