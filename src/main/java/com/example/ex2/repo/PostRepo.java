package com.example.ex2.repo;

import com.example.ex2.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, Long> {



}
