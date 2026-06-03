UPDATE users
SET password_hash = v.password_hash,
    status = v.status,
    updated_at = NOW()
    FROM (
    VALUES
        ('student01', '$2a$10$A1B2C3D4E5', 'ACTIVE'),
        ('student02', '$2a$10$F6G7H8I9J0', 'ACTIVE'),
        ('student03', '$2a$10$K1L2M3N4O5', 'INACTIVE'),
        ('student04', '$2a$10$P6Q7R8S9T0', 'ACTIVE'),
        ('student05', '$2a$10$U1V2W3X4Y5', 'ACTIVE'),

        ('teacher01', '$2a$10$Z6A7B8C9D0', 'ACTIVE'),
        ('teacher02', '$2a$10$E1F2G3H4I5', 'ACTIVE'),
        ('teacher03', '$2a$10$J6K7L8M9N0', 'INACTIVE'),
        ('teacher04', '$2a$10$O1P2Q3R4S5', 'ACTIVE'),

        ('admin01', '$2a$10$T6U7V8W9X0', 'ACTIVE')
) AS v(username, password_hash, status)
WHERE users.username = v.username;