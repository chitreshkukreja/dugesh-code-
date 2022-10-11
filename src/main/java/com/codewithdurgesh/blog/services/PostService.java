package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;

public interface PostService {

	// create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// update
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get all post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// get Single Post
	PostDto getPostById(Integer postId);

	// get all post by category
	List<PostDto> getAllPostByCategory(Integer categoryId);

	// get all post by user
	List<PostDto> getAllPostByUser(Integer userId);

	// search post
	List<PostDto> searchPosts(String keyword);
}
