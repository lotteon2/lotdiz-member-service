package com.lotdiz.memberservice.repository;

import com.lotdiz.memberservice.entity.Likes;
import com.lotdiz.memberservice.entity.LikesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, LikesId> {

    @Query("SELECT COUNT(*) FROM Likes l WHERE l.id.projectId = :projectId")
    Long countByProjectId(@Param("projectId") Long projectId);
}
