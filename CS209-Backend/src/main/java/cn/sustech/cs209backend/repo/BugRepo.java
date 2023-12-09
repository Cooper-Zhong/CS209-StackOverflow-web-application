package cn.sustech.cs209backend.repo;

import cn.sustech.cs209backend.entity.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepo extends JpaRepository<Bug,String> {
}
