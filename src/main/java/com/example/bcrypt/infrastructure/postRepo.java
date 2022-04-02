package com.example.bcrypt.infrastructure;

import com.example.bcrypt.domain.postModel;
import com.example.bcrypt.domain.userModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface postRepo extends JpaRepository<postModel,Long> {

}
