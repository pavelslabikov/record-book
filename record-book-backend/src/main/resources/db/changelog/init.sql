create table user_info
(
    id          uuid primary key,
    login       varchar     not null unique,
    password    varchar     not null,
    name        varchar     not null,
    surname     varchar     not null,
    patronymic  varchar     not null,
    system_role varchar     not null,
    created_at  timestamptz not null

);

create table student
(
    id_card_number      uuid primary key,
    user_id             uuid references user_info,
    course_number       int     not null,
    group_name          varchar not null,
    faculty             varchar not null,
    grade_book_number   varchar not null,
    specialization_code varchar not null
);


create table teacher
(
    id            serial primary key,
    user_id       uuid references user_info unique not null,
    academic_rank varchar                          not null,
    job_title     varchar                          not null


);

create table dean_employee
(
    id        serial primary key,
    user_id   uuid references user_info unique not null,
    job_title varchar                          not null
);



create table sheet
(
    id                   bigserial              not null,
    version              bigint                 not null,
    created_at           timestamptz            not null,
    deleted              bool default false     not null,
    subject_name         varchar                null,
    academic_year        varchar                null,
    credit_units         int                    null,
    exam_date            date                   null,
    diploma_form         varchar                null,
    topic                varchar                null,
    diploma_defense_date date                   null,
    protocol_date        date                   null,
    protocol_number      varchar                null,
    research_type        varchar                null,
    type                 varchar                not null,
    practice_place       varchar                null,
    primary key (id, version),
    teacher_id           int references teacher null

);

create table grade
(
    id            bigserial               not null,
    version       bigint                  not null,
    value         varchar                 null,
    student_id    uuid references student not null,
    created_at    timestamptz             not null,
    sheet_id      bigint                  not null,
    sheet_version bigint                  not null,
    primary key (id, version),
    deleted       bool default false      not null,
    foreign key (sheet_id, sheet_version) references sheet
);

create table sheet_changelog
(
    id          bigserial primary key,
    old_version bigint      null,
    new_version bigint      null,
    entity_id   bigint      not null,
    entity_type varchar     not null,
    operation   varchar     not null,
    author      uuid references user_info,
    created_at  timestamptz not null
);
