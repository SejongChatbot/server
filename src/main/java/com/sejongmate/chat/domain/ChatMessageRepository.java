package com.sejongmate.chat.domain;

import com.sejongmate.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
