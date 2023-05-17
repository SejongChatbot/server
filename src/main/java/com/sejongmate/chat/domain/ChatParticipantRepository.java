package com.sejongmate.chat.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatParticipantRepository extends JpaRepository<ChatRoom, Long> {
}
