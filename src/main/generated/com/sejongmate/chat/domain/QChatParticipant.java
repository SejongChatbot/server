package com.sejongmate.chat.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatParticipant is a Querydsl query type for ChatParticipant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatParticipant extends EntityPathBase<ChatParticipant> {

    private static final long serialVersionUID = -1997356165L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatParticipant chatParticipant = new QChatParticipant("chatParticipant");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QChatRoom room;

    public final com.sejongmate.user.domain.QUser user;

    public QChatParticipant(String variable) {
        this(ChatParticipant.class, forVariable(variable), INITS);
    }

    public QChatParticipant(Path<? extends ChatParticipant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatParticipant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatParticipant(PathMetadata metadata, PathInits inits) {
        this(ChatParticipant.class, metadata, inits);
    }

    public QChatParticipant(Class<? extends ChatParticipant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QChatRoom(forProperty("room")) : null;
        this.user = inits.isInitialized("user") ? new com.sejongmate.user.domain.QUser(forProperty("user")) : null;
    }

}

