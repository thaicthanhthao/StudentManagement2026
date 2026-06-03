INSERT INTO majors (major_code, major_name)
VALUES ('CNTT', 'Cong nghe thong tin'),
       ('KTPM', 'Ky thuat phan mem'),
       ('HTTT', 'He thong thong tin'),
       ('KHMT', 'Khoa hoc may tinh'),
       ('ATTT', 'An toan thong tin'),
       ('AI', 'Tri tue nhan tao'),
       ('DS', 'Khoa hoc du lieu'),
       ('MMT', 'Mang may tinh'),
       ('TKDH', 'Thiet ke do hoa'),
       ('QTKD', 'Quan tri kinh doanh');

INSERT INTO users(username, email, password_hash, role, status)
VALUES ('student01', 'student01@gmail.com', '123456', 'STUDENT', 'ACTIVE'),
       ('student02', 'student02@gmail.com', '123456', 'STUDENT', 'ACTIVE'),
       ('student03', 'student03@gmail.com', '123456', 'STUDENT', 'ACTIVE'),
       ('student04', 'student04@gmail.com', '123456', 'STUDENT', 'ACTIVE'),
       ('student05', 'student05@gmail.com', '123456', 'STUDENT', 'ACTIVE'),
       ('teacher01', 'teacher01@gmail.com', '123456', 'TEACHER', 'ACTIVE'),
       ('teacher02', 'teacher02@gmail.com', '123456', 'TEACHER', 'ACTIVE'),
       ('teacher03', 'teacher03@gmail.com', '123456', 'TEACHER', 'ACTIVE'),
       ('teacher04', 'teacher04@gmail.com', '123456', 'TEACHER', 'ACTIVE'),
       ('admin01', 'admin01@gmail.com', '123456', 'ADMIN', 'ACTIVE');

INSERT INTO people
    (full_name, dob, gender, phone, address, user_id)
SELECT v.full_name,
       v.dob,
       v.gender,
       v.phone,
       v.address,
       u.id
FROM (
         VALUES
             ('Nguyen Van A', DATE '2003-01-01', 'MALE', '0900000001', 'Da Nang', 'student01'),
             ('Nguyen Van B', DATE '2003-02-02', 'MALE', '0900000002', 'Da Nang', 'student02'),
             ('Nguyen Van C', DATE '2003-03-03', 'MALE', '0900000003', 'Da Nang', 'student03'),
             ('Nguyen Van D', DATE '2003-04-04', 'MALE', '0900000004', 'Da Nang', 'student04'),
             ('Nguyen Van E', DATE '2003-05-05', 'MALE', '0900000005', 'Da Nang', 'student05'),
             ('Tran Thi A', DATE '1985-01-01', 'FEMALE', '0900000006', 'Ha Noi', 'teacher01'),
             ('Tran Thi B', DATE '1986-02-02', 'FEMALE', '0900000007', 'Ha Noi', 'teacher02'),
             ('Tran Thi C', DATE '1987-03-03', 'FEMALE', '0900000008', 'Ha Noi', 'teacher03'),
             ('Tran Thi D', DATE '1988-04-04', 'FEMALE', '0900000009', 'Ha Noi', 'teacher04'),
             ('Admin User', DATE '1990-01-01', 'MALE', '0900000010', 'HCM', 'admin01')
     ) AS v(full_name, dob, gender, phone, address, username)
         JOIN users u
              ON u.username = v.username;

INSERT INTO students
    (person_id, student_code, enrollment_year, major_id, current_gpa)
SELECT p.id,
       v.student_code,
       v.enrollment_year,
       m.id,
       v.current_gpa
FROM (VALUES ('Nguyen Van A', 'SV001', 2021, 3.20, 'CNTT'),
             ('Nguyen Van B', 'SV002', 2021, 3.10, 'KTPM'),
             ('Nguyen Van C', 'SV003', 2022, 3.40, 'HTTT'),
             ('Nguyen Van D', 'SV004', 2022, 3.00, 'AI'),
             ('Nguyen Van E', 'SV005', 2023, 3.50,
              'DS')) v(full_name, student_code, enrollment_year, current_gpa, major_code)
         JOIN people p ON p.full_name = v.full_name
         JOIN majors m ON m.major_code = v.major_code;

INSERT INTO teachers
    (person_id, teacher_code, specialization)
SELECT p.id,
       v.teacher_code,
       v.specialization
FROM (VALUES ('Tran Thi A', 'GV001', 'Java'),
             ('Tran Thi B', 'GV002', 'Database'),
             ('Tran Thi C', 'GV003', 'Spring Boot'),
             ('Tran Thi D', 'GV004', 'AI')) v(full_name, teacher_code, specialization)
         JOIN people p
              ON p.full_name = v.full_name;

INSERT INTO subjects(subject_code, subject_name, credits)
VALUES ('SUB001', 'Java Core', 3),
       ('SUB002', 'Spring Boot', 3),
       ('SUB003', 'Database', 3),
       ('SUB004', 'Operating System', 3),
       ('SUB005', 'Computer Network', 3),
       ('SUB006', 'Data Structure', 3),
       ('SUB007', 'AI Basic', 3),
       ('SUB008', 'Machine Learning', 3),
       ('SUB009', 'Software Engineering', 3),
       ('SUB010', 'Web Development', 3);

INSERT INTO classes
(class_code,
 class_name,
 subject_id,
 teacher_id,
 semester,
 academic_year,
 room,
 day_of_week,
 start_time,
 end_time,
 max_students,
 status)
SELECT 'CLS' || LPAD(row_number() OVER()::text, 3, '0'),
       s.subject_name || ' Class',
       s.id,
       t.person_id,
       'HK1',
       '2025-2026',
       'A101',
       2,
       '07:00',
       '09:00',
       50,
       'OPEN'
FROM subjects s
         CROSS JOIN (SELECT person_id
                     FROM teachers LIMIT 1) t LIMIT 10;

INSERT INTO enrollments
(student_id,
 class_id,
 enrollment_date,
 status,
 attendance_score,
 assignment_score,
 midterm_score,
 final_exam_score,
 total_score,
 letter_grade,
 grade_point)
SELECT s.person_id,
       c.id,
       NOW(),
       'ACTIVE',
       8,
       8,
       7,
       9,
       8.0,
       'B+',
       3.5
FROM students s
         CROSS JOIN classes c LIMIT 10;