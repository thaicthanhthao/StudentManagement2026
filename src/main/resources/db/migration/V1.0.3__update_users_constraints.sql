ALTER TABLE users
    ALTER COLUMN status SET NOT NULL,
ALTER COLUMN status SET DEFAULT 'ACTIVE';

ALTER TABLE users
    ADD CONSTRAINT chk_users_role
        CHECK (role IN ('ADMIN', 'TEACHER', 'STUDENT'));

ALTER TABLE users
    ADD CONSTRAINT chk_users_status
        CHECK (status IN ('ACTIVE', 'INACTIVE'));