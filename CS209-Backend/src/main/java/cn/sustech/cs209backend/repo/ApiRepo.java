package cn.sustech.cs209backend.repo;

import cn.sustech.cs209backend.entity.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepo extends JpaRepository<Api, String> {
}
