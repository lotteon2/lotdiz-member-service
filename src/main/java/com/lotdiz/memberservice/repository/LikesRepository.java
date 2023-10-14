package com.lotdiz.memberservice.repository;

import com.lotdiz.memberservice.entity.Likes;
import com.lotdiz.memberservice.entity.LikesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, LikesId> {}
