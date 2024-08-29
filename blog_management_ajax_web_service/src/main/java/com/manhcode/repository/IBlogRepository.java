package com.manhcode.repository;

import com.manhcode.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlogRepository extends JpaRepository<Blog, Long> {
    // Tìm kiếm nội dung gần đúng
    List<Blog> findByContentContainingIgnoreCase(String content);

    // Nếu muốn tìm kiếm cả tiêu đề và nội dung
    List<Blog> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
}
