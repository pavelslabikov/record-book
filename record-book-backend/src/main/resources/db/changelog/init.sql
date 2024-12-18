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
    id_card_number      uuid,
    id                  int primary key,
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
    job_title   varchar                   not null
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
    student_id    int references student not null,
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

create table certificate_info
(
    id           uuid primary key,
    type         varchar     not null,
    not_before   date        null,
    not_after    date        null,
    content      bytea       not null,
    subject_info varchar     null,
    issuer_info varchar     null,
    created_at   timestamptz not null
);

create table signature_info
(
    id                uuid primary key,
    type              varchar     not null,
    certificate       uuid        null references certificate_info,
    signature_file    bytea       null,
    signed_at         timestamptz null,
    created_at        timestamptz not null
);


create table record_books_aggregation
(
    id                   bigint      not null,
    version              bigint      not null,
    period_start         date        not null,
    period_end           date        not null,
    signature            uuid        null references signature_info,
    author               uuid        not null references user_info,
    primary key (id, version),
    serialization_format varchar     not null,
    serialized_content   bytea       not null,
    hash_hex             varchar     not null,
    created_at           timestamptz not null
);

create table aggregation_status
(
    id                          bigserial primary key,
    aggregation_id              bigint      not null,
    aggregation_version         bigint      not null,
    signature_validation_result varchar     not null,
    signature_validation_reason varchar     null,
    integrity_validation_result varchar     not null,
    integrity_validation_reason varchar     null,
    last_updated_at             timestamptz not null,
    foreign key (aggregation_id, aggregation_version) references record_books_aggregation
);



