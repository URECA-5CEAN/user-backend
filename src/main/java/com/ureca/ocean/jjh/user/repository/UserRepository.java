package com.ureca.ocean.jjh.user.repository;



import com.ureca.ocean.jjh.user.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	boolean existsByNickname(String nickname);
	Optional<User> findByEmail(String email); 
}
