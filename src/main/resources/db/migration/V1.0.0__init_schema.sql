CREATE
    EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users
(
    id            UUID PRIMARY KEY             DEFAULT gen_random_uuid(),

    username      VARCHAR(150) UNIQUE NOT NULL,
    email         VARCHAR(200) UNIQUE NOT NULL,
    password_hash TEXT                NOT NULL,

    role          VARCHAR(30)         NOT NULL,
    status        VARCHAR(30)         NOT NULL DEFAULT 'ACTIVE',

    created_at    TIMESTAMPTZ                  DEFAULT now(),
    updated_at    TIMESTAMPTZ                  DEFAULT now()
);

CREATE TABLE people
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    full_name  VARCHAR(150) NOT NULL,
    dob        DATE,
    gender     VARCHAR(20),
    phone      VARCHAR(20),
    address    VARCHAR(255),

    user_id    UUID UNIQUE  NOT NULL,

    created_at TIMESTAMPTZ      DEFAULT now(),
    updated_at TIMESTAMPTZ      DEFAULT now(),

    CONSTRAINT fk_people_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);

CREATE TABLE majors
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    major_code VARCHAR(20) UNIQUE NOT NULL,
    major_name VARCHAR(100)       NOT NULL,

    created_at TIMESTAMPTZ      DEFAULT now(),
    updated_at TIMESTAMPTZ      DEFAULT now()
);

CREATE TABLE students
(
    person_id       UUID PRIMARY KEY,

    student_code    VARCHAR(50) UNIQUE NOT NULL,
    enrollment_year INT                NOT NULL,

    major_id        UUID               NOT NULL,

    current_gpa     DECIMAL(3, 2),

    created_at      TIMESTAMPTZ DEFAULT now(),
    updated_at      TIMESTAMPTZ DEFAULT now(),

    CONSTRAINT fk_student_person
        FOREIGN KEY (person_id)
            REFERENCES people (id),

    CONSTRAINT fk_student_major
        FOREIGN KEY (major_id)
            REFERENCES majors (id)
);

CREATE TABLE teachers
(
    person_id      UUID PRIMARY KEY,

    teacher_code   VARCHAR(50) UNIQUE NOT NULL,
    specialization VARCHAR(100),

    created_at     TIMESTAMPTZ DEFAULT now(),
    updated_at     TIMESTAMPTZ DEFAULT now(),

    CONSTRAINT fk_teacher_person
        FOREIGN KEY (person_id)
            REFERENCES people (id)
);

CREATE TABLE subjects
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    subject_code VARCHAR(20) UNIQUE NOT NULL,
    subject_name VARCHAR(100)       NOT NULL,

    credits      INT                NOT NULL,

    created_at   TIMESTAMPTZ      DEFAULT now(),
    updated_at   TIMESTAMPTZ      DEFAULT now()
);

CREATE TABLE classes
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    class_code    VARCHAR(30) UNIQUE NOT NULL,
    class_name    VARCHAR(100)       NOT NULL,

    subject_id    UUID               NOT NULL,
    teacher_id    UUID               NOT NULL,

    semester      VARCHAR(10)        NOT NULL,
    academic_year VARCHAR(20)        NOT NULL,

    room          VARCHAR(20),

    day_of_week   INT,

    start_time    TIME,
    end_time      TIME,

    max_students  INT                NOT NULL,

    status        VARCHAR(30)      DEFAULT 'OPEN',

    created_at    TIMESTAMPTZ      DEFAULT now(),
    updated_at    TIMESTAMPTZ      DEFAULT now(),

    CONSTRAINT fk_class_subject
        FOREIGN KEY (subject_id)
            REFERENCES subjects (id),

    CONSTRAINT fk_class_teacher
        FOREIGN KEY (teacher_id)
            REFERENCES teachers (person_id)
);

CREATE TABLE enrollments
(
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    student_id       UUID      NOT NULL,
    class_id         UUID      NOT NULL,

    enrollment_date  TIMESTAMP NOT NULL,

    status           VARCHAR(30),

    attendance_score DECIMAL(5, 2),
    assignment_score DECIMAL(5, 2),
    midterm_score    DECIMAL(5, 2),
    final_exam_score DECIMAL(5, 2),

    total_score      DECIMAL(5, 2),

    letter_grade     VARCHAR(5),

    grade_point      DECIMAL(3, 2),

    created_at       TIMESTAMPTZ      DEFAULT now(),
    updated_at       TIMESTAMPTZ      DEFAULT now(),

    CONSTRAINT fk_enrollment_student
        FOREIGN KEY (student_id)
            REFERENCES students (person_id),

    CONSTRAINT fk_enrollment_class
        FOREIGN KEY (class_id)
            REFERENCES classes (id),

    CONSTRAINT uk_student_class
        UNIQUE (student_id, class_id)
);