create table users
(
    account_id   integer not null
        primary key,
    display_name text,
    link         text,
    reputation   integer not null
);

create table tags
(
    tag_name text not null
        primary key
);
create table apis
(
    api_name text not null
        primary key
);

create table bugs
(
    bug_name text not null
        primary key
);

create table questions
(
    question_id        integer      not null
        primary key,
    account_id         integer      not null
        constraint fk_questions_users
            references users,
    answer_count       integer      not null,
    body               text         not null,
    content_license    text,
    creation_date      timestamp(6) not null,
    is_answered        boolean      not null,
    last_activity_date timestamp(6) not null,
    last_edit_date     timestamp(6),
    link               text         not null,
    score              integer      not null,
    title              text         not null,
    view_count         integer      not null
);



create table answers
(
    answer_id          integer      not null
        primary key,
    account_id         integer      not null
        constraint fk_answers_users
            references users,
    body               text         not null,
    content_license    text,
    creation_date      timestamp(6) not null,
    is_accepted        boolean      not null,
    last_activity_date timestamp(6) not null,
    last_edit_date     timestamp(6),
    question_id        integer      not null,
    score              integer      not null
);

create table comments
(
    comment_id      integer      not null
        primary key,
    account_id      integer      not null
        constraint fk_comments_users
            references users,
    post_id         integer      not null, -- question_id or answer_id!
    body            text         not null,
    content_license text,
    creation_date   timestamp(6) not null,
    edited          boolean      not null,
    score           integer      not null
);

create table questions_tags
(
    question_id integer not null
        constraint fk_question_tags_questions
            references questions,
    tag_name    text    not null
        constraint fk_question_tags_tags
            references tags,
    primary key (question_id, tag_name)
);

create table questions_apis
(
    question_id integer not null
        constraint fk_question_api_questions
            references questions,
    api_name    text    not null
        constraint fk_question_api_api_name
            references apis,
    count       integer not null,
    primary key (question_id, api_name)
);

create table comments_apis
(
    comment_id integer not null
        constraint fk_comment_api_comments
            references comments,
    api_name   text    not null
        constraint fk_comment_api_api_name
            references apis,
    count      integer not null,
    primary key (comment_id, api_name)
);

create table answers_apis
(
    answer_id integer not null
        constraint fk_answer_api_answers
            references answers,
    api_name  text    not null
        constraint fk_answer_api_api_name
            references apis,
    count     integer not null,
    primary key (answer_id, api_name)
);

create table questions_bugs
(
    question_id integer not null
        constraint fk_question_bug_questions
            references questions,
    bug_name    text    not null
        constraint fk_question_bug_bugs
            references bugs,
    count       integer not null,
    primary key (question_id, bug_name)
);

create table comments_bugs
(
    comment_id integer not null
        constraint fk_comment_bug_comments
            references comments,
    bug_name   text    not null
        constraint fk_comment_bug_bugs
            references bugs,
    count      integer not null,
    primary key (comment_id, bug_name)
);

create table answers_bugs
(
    answer_id integer not null
        constraint fk_answer_bug_answers
            references answers,
    bug_name  text    not null
        constraint fk_answer_bug_bugs
            references bugs,
    count     integer not null,
    primary key (answer_id, bug_name)
);

create table topic_intimacy
(
    topic1   text             not null,
    topic2   text             not null,
    intimacy double precision not null,
    primary key (topic1, topic2)
);



