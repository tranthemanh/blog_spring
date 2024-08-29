package com.manhcode.service;

import com.manhcode.model.Blog;

import java.util.Optional;

public interface IBlogService {
    Iterable<Blog> findAll();

    Optional<Blog> findById(Long id);

    Blog save(Blog blog);

    void remove(Long id);
}
