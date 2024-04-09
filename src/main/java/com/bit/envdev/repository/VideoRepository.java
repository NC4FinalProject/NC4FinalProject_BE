package com.bit.envdev.repository;

import com.bit.envdev.entity.Video;
import com.bit.envdev.entity.VideoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, VideoId> {
}
