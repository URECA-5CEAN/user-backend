package com.ureca.ocean.jjh.community.service.impl;


import com.ureca.ocean.jjh.client.MapClient;
import com.ureca.ocean.jjh.client.dto.BrandDto;
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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MapClient mapClient;
    @Override
    public PostResponseDto insertPost(String email, PostRequestDto postRequestDto){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserException(ErrorCode.NOT_FOUND_USER));

        //brand id를 넣으면 brand name을 갖고 오기
        BrandDto brandDto=mapClient.getBrandNameById(postRequestDto.getBrandId());

        //혜택 id를 넣으면 혜택 name을 갖고 오기
        String benefitName=mapClient.getBenefitNameById(postRequestDto.getBenefitId());

        //동 ( 마지막 단어 ) 만 저장
        String[] words = postRequestDto.getLocation().trim().split("\\s+");
        log.info(words[words.length - 1]);
        Post post = Post.builder()
                        .title(postRequestDto.getTitle())
                        .content(postRequestDto.getContent())
                        .author(user)
                        .category(postRequestDto.getCategory())
                        .brandName(brandDto.getName())
                        .brandImgUrl(brandDto.getImage_url())
                        .benefitName(benefitName)
                        .promiseDate(postRequestDto.getPromiseDate())
                        .location(words[words.length - 1])
                        .build();

        Post newPost = postRepository.save(post);
        return PostResponseDto.of(newPost);
    }

    @Override
    public List<PostResponseDto> getMyPost(int pageNo, String criteria, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UserException(ErrorCode.NOT_FOUND_USER));
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, criteria));
        List<Post> postList = postRepository.findByAuthor(user,pageable).getContent();
        List<PostResponseDto> listPostResponseDto = new ArrayList<>();
        for(Post post : postList){
            log.info("post 순회중 : " + post.getId());
            listPostResponseDto.add(
                    PostResponseDto.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .location(post.getLocation())
                            .author(UserResponseDto.of(post.getAuthor()))
                            .category(post.getCategory())
                            .benefitName(post.getBenefitName())
                            .brandName(post.getBrandName())
                            .brandImgUrl(post.getBrandImgUrl())
                            .promiseDate(post.getPromiseDate())
                            .build()
            );
        }
        return listPostResponseDto;
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
            log.info("post 순회중 : " + post.getId());
            listPostResponseDto.add(
                    PostResponseDto.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .location(post.getLocation())
                        .author(UserResponseDto.of(post.getAuthor()))
                        .category(post.getCategory())
                        .benefitName(post.getBenefitName())
                        .brandName(post.getBrandName())
                        .brandImgUrl(post.getBrandImgUrl())
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

    @Override
    public void deletePost(String email, UUID postId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserException(ErrorCode.POST_NOT_FOUND));

        // 작성자만 삭제 가능
        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new UserException(ErrorCode.NOT_AUTHORIZED); // 직접 정의했거나 HttpStatus.FORBIDDEN 사용 가능
        }

        postRepository.delete(post);
    }

    @Override
    public PostResponseDto updatePost(String email, UUID postId, PostRequestDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserException(ErrorCode.POST_NOT_FOUND));

        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new UserException(ErrorCode.NOT_AUTHORIZED);
        }

        BrandDto brandDto = mapClient.getBrandNameById(dto.getBrandId());
        String benefitName = mapClient.getBenefitNameById(dto.getBenefitId());
        String[] words = dto.getLocation().trim().split("\\s+");
        String lastLocation = words[words.length - 1];

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCategory(dto.getCategory());
        post.setBrandName(brandDto.getName());
        post.setBrandImgUrl(brandDto.getImage_url());
        post.setBenefitName(benefitName);
        post.setPromiseDate(dto.getPromiseDate());
        post.setLocation(lastLocation);

        return PostResponseDto.of(post);
    }


}
