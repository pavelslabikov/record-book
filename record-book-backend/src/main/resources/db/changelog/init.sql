create table user_info
(
    id          uuid primary key,
    login       varchar            not null unique,
    password    varchar            not null,
    name        varchar            not null,
    surname     varchar            not null,
    patronymic  varchar            not null,
    system_role varchar            not null,
    created_at  timestamptz        not null,
    deleted     bool default false not null

);

create table student
(
    id_card_number      uuid primary key,
    user_id             uuid references user_info,
    course_number       int                not null,
    group_name          varchar            not null,
    faculty             varchar            not null,
    grade_book_number   varchar            not null,
    deleted             bool default false not null,
    specialization_code varchar            not null
);


create table teacher
(
    id            int primary key,
    user_id       uuid references user_info not null,
    academic_rank varchar                   not null,
    deleted       bool default false        not null,
    job_title     varchar                   not null


);

create table dean_employee
(
    id          int primary key,
    user_id     uuid references user_info not null,
    deleted     bool default false        not null,
    job_title   varchar                   not null,
    certificate bytea                     null
);



create table sheet
(
    id                   bigint                 not null,
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
    id            bigserial               not null primary key,
    value         varchar                 null,
    student_id    uuid references student not null,
    created_at    timestamptz             not null,
    sheet_id      bigint                  not null,
    sheet_version bigint                  not null,
    deleted       bool default false      not null,
    foreign key (sheet_id, sheet_version) references sheet
);

create table sheet_changelog
(
    id          bigserial primary key,
    old_version bigint      null,
    new_version bigint      null,
    entity_id   bigint      not null,
    operation   varchar     not null,
    author      uuid references user_info,
    created_at  timestamptz not null
);

create table record_books_aggregation
(
    id                          uuid primary key,
    signature_validation_result varchar     not null,
    signature_file              bytea       null,
    original_file               bytea       null,
    file_digest                 bytea       null,
    signature_validation_reason varchar     null,
    period_start                date        not null,
    period_end                  date        not null,
    created_at                  timestamptz not null
);
