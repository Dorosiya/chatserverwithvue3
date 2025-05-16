package com.example.chatserver.chat.repository;

import com.example.chatserver.chat.domain.ChatParticipant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatParticipantRepository extends CrudRepository<ChatParticipant, Long> {
}
