package com.ureca.ocean.jjh.chat.service.impl;

import com.ureca.ocean.jjh.chat.dto.ChatRoomResponseDto;
import com.ureca.ocean.jjh.chat.entity.ChatRoom;
import com.ureca.ocean.jjh.chat.entity.ChatRoomUser;
import com.ureca.ocean.jjh.chat.repository.ChatRoomRepository;
import com.ureca.ocean.jjh.chat.repository.ChatRoomUserRepository;
import com.ureca.ocean.jjh.chat.service.ChatRoomService;
import com.ureca.ocean.jjh.community.entity.Post;
import com.ureca.ocean.jjh.community.repository.PostRepository;
import com.ureca.ocean.jjh.exception.ErrorCode;
import com.ureca.ocean.jjh.exception.UserException;
import com.ureca.ocean.jjh.user.entity.User;
import com.ureca.ocean.jjh.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    @Transactional
    public ChatRoomResponseDto insertChatRoom(String email, UUID postId) {

        // 로그인한 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        //채팅방이 할당될 글
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserException(ErrorCode.POST_NOT_FOUND));

        User author = post.getAuthor();

        Post newPost = postRepository.save(post);
        ChatRoom chatRoom = ChatRoom.builder()
                .post(newPost)
                .build();

        ChatRoom newChatRoom = chatRoomRepository.save(chatRoom);


        ChatRoomUser chatRoomUser = chatRoomUserRepository.save(ChatRoomUser.builder().user(author).chatRoom(newChatRoom).build());
        ChatRoomUser chatRoomUser2 = chatRoomUserRepository.save(ChatRoomUser.builder().user(user).chatRoom(newChatRoom).build());


        return ChatRoomResponseDto.builder()
                .ChatRoomId(newChatRoom.getId())
                .user1Id(chatRoomUser.getUser().getId())
                .user2Id(chatRoomUser2.getUser().getId())
                .build();
    }



}
