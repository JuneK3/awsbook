package com.rootlab.awsbook.service.posts;

import com.rootlab.awsbook.domain.posts.Posts;
import com.rootlab.awsbook.domain.posts.PostsRepository;
import com.rootlab.awsbook.web.dto.PostsResponseDto;
import com.rootlab.awsbook.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
	private	final PostsRepository postsRepository;

	@Transactional
	public Long save(PostsSaveRequestDto requestDto){
		return postsRepository.save(requestDto.toEntity()).getId();
	}

	@Transactional
	public Long update(Long id, PostsSaveRequestDto requestDto) {
		Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		posts.update(requestDto.getTitle(), requestDto.getContent());
		return id;
	}

	public PostsResponseDto findById(Long id){
		Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		return new PostsResponseDto(entity);
	}
}
