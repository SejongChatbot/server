package com.sejongmate.post.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostScrap is a Querydsl query type for PostScrap
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostScrap extends EntityPathBase<PostScrap> {

    private static final long serialVersionUID = 1407004041L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostScrap postScrap = new QPostScrap("postScrap");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPost post;

    public final com.sejongmate.user.domain.QUser user;

    public QPostScrap(String variable) {
        this(PostScrap.class, forVariable(variable), INITS);
    }

    public QPostScrap(Path<? extends PostScrap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostScrap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostScrap(PathMetadata metadata, PathInits inits) {
        this(PostScrap.class, metadata, inits);
    }

    public QPostScrap(Class<? extends PostScrap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.sejongmate.user.domain.QUser(forProperty("user")) : null;
    }

}

