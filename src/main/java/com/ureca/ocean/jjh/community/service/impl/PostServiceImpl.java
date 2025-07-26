package com.ureca.ocean.jjh.community.service.impl;


import com.ureca.ocean.jjh.client.MapClient;
import com.ureca.ocean.jjh.community.dto.response.PostResponseDto;
import com.ureca.ocean.jjh.community.dto.request.PostRequestDto;
import com.ureca.ocean.jjh.community.entity.Post;
import com.ureca.ocean.jjh.community.repository.PostRepository;
import com.ureca.ocean.jjh.community.service.PostService;
import com.ureca.ocean.jjh.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.user.dto.response.UserResponseDto;
import com.ureca.ocean.jjh.user.entity.User;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MapClient mapClient;
    @Override
    public PostResponseDto insertPost(String email, PostRequestDto postRequestDto){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserException(ErrorCode.NOT_FOUND_USER));

        Post post = Post.builder()
                        .title(postRequestDto.getTitle())
                        .content(postRequestDto.getContent())
                        .author(user)
                        .category(postRequestDto.getCategory())
                        .brandId(postRequestDto.getBrandId())
                        .benefitId(postRequestDto.getBenefitId())
                        .promiseDate(postRequestDto.getPromiseDate())
                        .location(User.getDong(user.getAddress()))
                        .build();

        Post newPost = postRepository.save(post);
        return PostResponseDto.of(newPost);
    }
    //refactoring 필요
    @Override
    public List<PostResponseDto> listPost(int pageNo, String criteria, String location){

        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, criteria));
        List<Post> postList;
        if(location.isEmpty()){
            postList =  postRepository.findAll(pageable).getContent();
        }else{
            postList =  postRepository.findByLocation(pageable,location).getContent();;//.map(PostResponseDto::of).getContent(); //of : entity->dto
        }
        List<PostResponseDto> listPostResponseDto = new ArrayList<>();
        for(Post post : postList){
            //brand id를 넣으면 brand name을 갖고 오기
            String brandName=mapClient.getBrandNameById(post.getBrandId());


            //혜택 id를 넣으면 혜택 name을 갖고 오기
            String benefitName=mapClient.getBenefitNameById(post.getBenefitId());


            listPostResponseDto.add(
                    PostResponseDto.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .location(post.getLocation())
                        .author(UserResponseDto.of(post.getAuthor()))
                        .category(post.getCategory())
                        .benefitName(benefitName)
                        .brandName(brandName)
                        .promiseDate(post.getPromiseDate())
                        .build()
                    );

        }

        return listPostResponseDto;
    }
    @Override
    public PostResponseDto detailPost(UUID postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserException(ErrorCode.POST_NOT_FOUND));
        return PostResponseDto.of(post);
    }
    @Override
    public List<String> listLocations(){
        return postRepository.findDistinctLocations();
    }
}
