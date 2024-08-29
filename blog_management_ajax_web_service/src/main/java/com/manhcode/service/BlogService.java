package com.manhcode.service;

import com.manhcode.model.Blog;
import com.manhcode.repository.IBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements IBlogService{

    @Autowired
    private IBlogRepository blogRepository;

    @Override
    public Iterable<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public void remove(Long id) {
        blogRepository.deleteById(id);
    }

    public List<Blog> searchBlogs(String query) {
        // Tìm kiếm gần đúng bằng cách sử dụng phương thức findByContentContainingIgnoreCase
        return blogRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query);
    }
    public List<Blog> findBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return blogRepository.findAll(pageable).getContent();
    }
}
