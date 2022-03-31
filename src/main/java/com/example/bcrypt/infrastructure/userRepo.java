package com.example.bcrypt.infrastructure;

import com.example.bcrypt.domain.userModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepo extends JpaRepository<userModel,Long> {

    userModel findByUsername(String username);

}
