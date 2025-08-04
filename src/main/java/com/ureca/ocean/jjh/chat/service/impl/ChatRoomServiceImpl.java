package com.ureca.ocean.jjh.chat.service.impl;

import com.ureca.ocean.jjh.chat.dto.ChatMessageDto;
import com.ureca.ocean.jjh.chat.dto.ChatRoomMessageResponseDto;
import com.ureca.ocean.jjh.chat.dto.ChatRoomResponseDto;
import com.ureca.ocean.jjh.chat.entity.ChatRoom;
import com.ureca.ocean.jjh.chat.entity.ChatRoomUser;
import com.ureca.ocean.jjh.chat.repository.ChatMessageRepository;
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
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageRepository chatMessageRepository;
    @Transactional
    @Override
    public ChatRoomResponseDto insertChatRoom(String email, UUID postId) {

        // 로그인한 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));

        //채팅방이 할당될 글
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserException(ErrorCode.POST_NOT_FOUND));

        //상대방 사용자
        User author = post.getAuthor();

        List<ChatRoom> chatRoomsBetweenMeAndOther = chatRoomUserRepository.findByParticipants(user,author);
        for(ChatRoom chatRoom:chatRoomsBetweenMeAndOther){
            Post postCheck = chatRoom.getPost();
            if(post.getId() == postCheck.getId()){
                return ChatRoomResponseDto.from(chatRoom,user,author,post);
            }
        }
        ChatRoom chatRoom = ChatRoom.builder()
                .post(post)
                .build();

        ChatRoom newChatRoom = chatRoomRepository.save(chatRoom);


        ChatRoomUser me = chatRoomUserRepository.save(ChatRoomUser.builder().user(user).chatRoom(newChatRoom).build());
        ChatRoomUser authorChatRoomUser = chatRoomUserRepository.save(ChatRoomUser.builder().user(author).chatRoom(newChatRoom).build());

        return ChatRoomResponseDto.from(newChatRoom,me.getUser(),authorChatRoomUser.getUser(),post);
    }

    @Override
    public List<ChatRoomMessageResponseDto> getChatRoomMessages(UUID chatRoomId){
        List<ChatRoomMessageResponseDto> chatMessageDtoWithNameList = new ArrayList<>();
        for(ChatMessageDto chatMessageDto:  chatMessageRepository.findByChatRoomIdOrderByTimeDesc(chatRoomId)){
            String userName = userRepository.findById(chatMessageDto.getUserId()).get().getName();
            chatMessageDtoWithNameList.add(
                    ChatRoomMessageResponseDto.builder()
                        .userName(userName)
                        .message(chatMessageDto.getMessage())
                        .time(chatMessageDto.getTime())
                        .build()
            );
        }
        return chatMessageDtoWithNameList;
    }

    public List<ChatRoomResponseDto> getChatRoom(String email){
        
        //현재 로그인한 나 찾기
        User me = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));
        
        
        //나와 chatting방 목록들의 관계사항 찾아오기
        List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findByUser(me);

        //관계사항에서 가공해야할 채팅방 목록 뽑기
        List<ChatRoom> chatRoomList = new ArrayList<>();
        for(ChatRoomUser chatRoomUser : chatRoomUsers){
            chatRoomList.add(chatRoomRepository.findById(chatRoomUser.getChatRoom().getId()).orElseThrow(()-> new UserException(ErrorCode.NOT_FOUND_CHATROOM)));
        }
        
        //채팅방 목록에서 상대방을 구한 후에 dto로 변환하기
        List<ChatRoomResponseDto> chatRoomResponseDtoList = new ArrayList<>();
        for( ChatRoom chatRoom : chatRoomList){
            //채팅방에 소속돼있지만, 내가 아닌 사용자 즉, 상대방이 누구인지 추출
            ChatRoomUser other = chatRoomUserRepository.findByChatRoomButNotMe(chatRoom, me).orElseThrow(()-> new UserException(ErrorCode.NOT_FOUND_USER));
            chatRoomResponseDtoList.add(
                    ChatRoomResponseDto.from(chatRoom,me,other.getUser(),chatRoom.getPost())
                    );
        }

        return chatRoomResponseDtoList;
    }



}
