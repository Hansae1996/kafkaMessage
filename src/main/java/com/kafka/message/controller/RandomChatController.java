package com.kafka.message.controller;

import com.kafka.message.model.ChatMessage;
import com.kafka.message.service.ChatProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.LinkedList;
import java.util.Queue;

@Controller
public class RandomChatController {
    private final ChatProducer chatProducer;
    private final SimpMessagingTemplate messagingTemplate;
    private Queue<String> waitingUsers = new LinkedList<>();

    @Autowired
    public RandomChatController(ChatProducer chatProducer, SimpMessagingTemplate messagingTemplate) {
        this.chatProducer = chatProducer;
        this.messagingTemplate = messagingTemplate;
    }

    // 랜덤 채팅 시작 요청 처리
    @MessageMapping("/startRandomChat")
    public void startRandomChat(ChatMessage chatMessage) {
        String userId = chatMessage.getUserId();  // 사용자 ID 받아오기
        System.out.println("랜덤 채팅 요청 수신 - 사용자 ID: " + userId);

        if (!waitingUsers.isEmpty()) {
            // 대기 중인 사용자와 매칭하여 채팅 시작
            String matchedUser = waitingUsers.poll();  // 대기 중인 사용자 꺼내기
            System.out.println("매칭 성공: " + userId + " <-> " + matchedUser);

            // 매칭된 사용자들에게 매칭 성공 메시지 전송
            messagingTemplate.convertAndSendToUser(userId, "/topic/chat", new ChatMessage(userId, "시스템", "매칭 성공!"));
            messagingTemplate.convertAndSendToUser(matchedUser, "/topic/chat", new ChatMessage(matchedUser, "시스템", "매칭 성공!"));
        } else {
            // 대기 사용자 목록에 추가
            waitingUsers.add(userId);
            System.out.println("사용자가 대기열에 추가됨: " + userId);

            // 대기 중 메시지 전송
            messagingTemplate.convertAndSendToUser(userId, "/topic/chat", new ChatMessage(userId, "시스템", "대기 중..."));
        }
    }

    // 메시지 전송 처리
    @MessageMapping("/sendMessage")
    public void sendMessage(ChatMessage message) {
        messagingTemplate.convertAndSend("/topic/chat", message);  // 모든 사용자에게 메시지 전송
    }
}
