USE schedule_app;

/* schedules(일정) 테이블 생성 */
CREATE TABLE schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자',
    username VARCHAR(100) NOT NULL COMMENT '작성 유저명',
    title VARCHAR(255) NOT NULL COMMENT '할 일 제목',
    contents LONGTEXT COMMENT '할 일 내용',
    created_at DATETIME NOT NULL COMMENT '일정 생성일',
    modified_at DATETIME NOT NULL COMMENT '일정 수정일'
);

/* users(작성자) 테이블 생성 */
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '유저 식별자',
    name VARCHAR(100) NOT NULL COMMENT '작성 유저 이름',
    email VARCHAR(255) COMMENT '작성 유저 이메일',
    created_at DATETIME COMMENT '등록일',
    modified_at DATETIME COMMENT '수정일'
);

/* schedules > user_id 컬럼 추가 */
ALTER TABLE schedules
ADD COLUMN user_id BIGINT NOT NULL DEFAULT 0 COMMENT '유저 식별자 ID' AFTER id;

/* users > name, created_at, modified_at 데이터 값을 schedules 테이블에서 가져와서 데이터 삽입 */
INSERT INTO users(name, created_at, modified_at)
SELECT schedules.username, schedules.created_at, schedules.modified_at
FROM schedules
GROUP BY schedules.username, schedules.created_at, schedules.modified_at;


/* schedules 와 users 테이블 JOIN > s.user_id 에 u.id 값 없데이트 */
UPDATE schedules s
    JOIN users u ON s.username = u.name
SET s.user_id = u.id
WHERE s.user_id = 0;

/* user_id 외래 키(FK) 선언 */
ALTER TABLE schedules
    ADD CONSTRAINT FK_schedules_users
        FOREIGN KEY (user_id) REFERENCES users(id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE;


ALTER TABLE schedules
    DROP COLUMN username;

/* users > password 컬럼 추가 */
ALTER TABLE users
ADD COLUMN password VARCHAR(255) COMMENT "비밀번호" AFTER email;

CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '댓글 식별자',
    comment LONGTEXT COMMENT '댓글 내용',
    user_id BIGINT NOT NULL DEFAULT 0 COMMENT '유저 식별자 ID',
    user_name VARCHAR(100) COMMENT '작성 유저 이름',
    schedule_title VARCHAR(255) COMMENT '할 일 제목',
    schedule_id BIGINT NOT NULL DEFAULT 0 COMMENT '일정 식별자 ID',
    created_at DATETIME COMMENT '등록일',
    modified_at DATETIME COMMENT '수정일'
);

INSERT INTO comments(user_name)
SELECT u.name
FROM users u
GROUP BY u.name;

INSERT INTO comments(schedule_title)
SELECT s.title
FROM schedules s
GROUP BY s.title;

UPDATE comments c
    JOIN users u ON c.user_name = u.name
    JOIN schedules s ON c.schedule_title = s.title
SET c.user_id = u.id, c.schedule_id = s.id
WHERE c.user_id = 0 OR c.schedule_id = 0;

ALTER TABLE comments
    ADD CONSTRAINT FK_comments_users
        FOREIGN KEY (user_id) REFERENCES users(id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE;

ALTER TABLE comments
    ADD CONSTRAINT FK_comments_schedules
        FOREIGN KEY (schedule_id) REFERENCES schedules(id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE;

ALTER TABLE comments
    DROP COLUMN user_name,
    DROP COLUMN schedule_title;