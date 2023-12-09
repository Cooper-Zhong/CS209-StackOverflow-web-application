package cn.sustech.cs209backend.repo;

import cn.sustech.cs209backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends JpaRepository<Tag, String> {
}
