insert into user_info (id, login, password, surname, name, patronymic, system_role, created_at, deleted)
values
    ('1b38336a-0066-4469-8e05-9c1994f0af97', 'pslabikov', '123', 'Слабиков', 'Павел', 'Владимирович', 'ADMIN', now(), false),
    ('5f717f66-469d-485a-9224-b6e78eaaac22', 'petrov', '123', 'Петров', 'Пётр', 'Петрович', 'USER', now(), false),
    ('e44cf214-cd62-4eef-b31e-8ba4e479f353', 'sidorov', '123', 'Сидоров', 'Семён', 'Семёнович', 'USER', now(), false),
    ('b7f11b2a-a3ad-4f65-9a60-a400eb22dff7', 'dmitrov', '123', 'Дмитров', 'Дмитрий', 'Дмитриевич', 'USER', now(), false),
    ('73db5c34-1fd8-4e09-b2a4-ecac96d64334', 'ivanov', '123', 'Иванов', 'Иван', 'Иванович', 'USER', now(), false);


insert into student (id, id_card_number, user_id, course_number, group_name, faculty, grade_book_number, deleted,
                     specialization_code)
values
    (1, 'a1a3a2d7-7995-4d2d-bf17-b534a134517d', '5f717f66-469d-485a-9224-b6e78eaaac22', 1, 'МЕН-191015', 'ИЕНиМ', '000001', false, '10.05.01'),
    (2, 'c0d5336c-3c0b-43bf-b751-48cf96b223f3', 'e44cf214-cd62-4eef-b31e-8ba4e479f353', 1, 'МЕН-191015', 'ИЕНиМ', '000002', false, '10.05.01');

insert into teacher (id, user_id, academic_rank, deleted, job_title)
values (1, 'b7f11b2a-a3ad-4f65-9a60-a400eb22dff7', 'Профессор', false, 'Профессор');

insert into dean_employee (id, user_id, deleted, job_title)
values (1, '73db5c34-1fd8-4e09-b2a4-ecac96d64334', false, 'Секретарь');