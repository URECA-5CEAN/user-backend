package com.ureca.ocean.jjh.community.repository;

import com.ureca.ocean.jjh.community.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface PostRepository  extends JpaRepository<Post, UUID> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByLocation(Pageable pageable,String location);
}
