package com.manhcode.controller;

import com.manhcode.model.Blog;
import com.manhcode.service.BlogService;
import com.manhcode.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping
    public ResponseEntity<List<Blog>> listBlogs(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        List<Blog> blogs = blogService.findBlogs(page, size);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        return new ResponseEntity<>(blogService.save(blog), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Blog> deleteBlog(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        if (!blog.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        blogService.remove(id);
        return new ResponseEntity<>(blog.get(), HttpStatus.NO_CONTENT);
    }

    // Phương thức tìm kiếm blog
    @GetMapping("/search")
    public ResponseEntity<List<Blog>> searchBlogs(@RequestParam("q") String query) {
        List<Blog> blogs = blogService.searchBlogs(query);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }


}
