DROP DATABASE IF EXISTS my_pan;
CREATE DATABASE my_pan;
USE my_pan;

CREATE TABLE plan
(
    id                   bigint PRIMARY KEY AUTO_INCREMENT,
    name                 varchar(64) UNIQUE NOT NULL,
    storage_limit        bigint             NOT NULL,
    upload_limit         bigint             NOT NULL,
    download_limit       bigint             NOT NULL,
    download_concurrency bigint             NOT NULL,
    upload_concurrency   bigint             NOT NULL,
    file_size_limit      bigint             NOT NULL
) COMMENT '套餐' DEFAULT CHARACTER SET 'utf8mb4';

INSERT INTO plan(name, storage_limit,
                 upload_limit, download_limit,
                 upload_concurrency, download_concurrency,
                 file_size_limit)
VALUES ('free_plan', 67108864, 65536, 65536, 1, 1, 33554432),
       ('master_plan', 4294967296, 67108864, 67108864, 65536, 65536, 2147483648);

CREATE TABLE user
(
    id            bigint PRIMARY KEY AUTO_INCREMENT,
    phone_number  varchar(20) UNIQUE NULL,
    `password`    varchar(255)       NULL,
    nickname      varchar(255)       NULL,
    dir_root      varchar(255)       NOT NULL,
    create_time   timestamp          NOT NULL DEFAULT current_timestamp,
    plan_id       bigint             NOT NULL REFERENCES plan (id),
    storage_usage bigint             NOT NULL DEFAULT 0
) COMMENT '用户' DEFAULT CHARACTER SET 'utf8mb4';

CREATE INDEX user_phone_number_index ON user (phone_number);
CREATE INDEX user_plan_index ON user (plan_id);

INSERT INTO user (phone_number, nickname, dir_root, plan_id)
VALUES ('13354856750', 'admin', '1', 2);

CREATE TABLE file
(
    id               bigint PRIMARY KEY AUTO_INCREMENT,
    owner_id         bigint        NOT NULL REFERENCES user (id),
    name             varchar(255)  NOT NULL,
    path             varchar(2048) NOT NULL,
    size             bigint        NOT NULL,
    disk_usage       bigint        NOT NULL,
    status           varchar(10)   NOT NULL,
    create_time      timestamp     NOT NULL DEFAULT current_timestamp,
    digest           varchar(255)  NULL,
    digest_algorithm varchar(16)   NULL
) COMMENT '文件' DEFAULT CHARACTER SET 'utf8mb4';

CREATE INDEX file_owner_id_path_index ON file (owner_id, path(760));

CREATE TABLE folder
(
    id          bigint PRIMARY KEY AUTO_INCREMENT,
    name        varchar(255)  NOT NULL,
    path        varchar(2048) NOT NULL,
    owner_id    bigint        NOT NULL REFERENCES user (id),
    create_time timestamp     NOT NULL DEFAULT current_timestamp
) COMMENT '文件夹' DEFAULT CHARACTER SET 'utf8mb4';

CREATE INDEX folder_owner_id_path_index ON folder (owner_id, path(760));

CREATE TABLE activity
(
    id         bigint PRIMARY KEY AUTO_INCREMENT,
    type       char(10) NOT NULL,
    owner_id   bigint   NOT NULL REFERENCES user (id),
    file_id    bigint   NOT NULL REFERENCES file (id),
    total_clip bigint   NOT NULL,
    used_clip  bigint   NOT NULL
) COMMENT '任务' DEFAULT CHARACTER SET 'utf8mb4';

CREATE INDEX activity_file_id_index ON activity (file_id);
CREATE INDEX activity_owner_id_index ON activity (owner_id);


CREATE TABLE file_cache
(
    id         bigint PRIMARY KEY AUTO_INCREMENT,
    file_id    bigint     NOT NULL REFERENCES file (id),
    type       varchar(5) NOT NULL,
    read_count int        NULL
) COMMENT '文件缓存' DEFAULT CHARACTER SET 'utf8mb4';

CREATE INDEX file_cache_file_id_index ON file_cache (file_id);

CREATE TABLE server_status
(
    id    bigint PRIMARY KEY AUTO_INCREMENT,
    `key` varchar(255)  NOT NULL,
    value varchar(2048) NOT NULL
) COMMENT '服务端状态' DEFAULT CHARACTER SET 'utf8mb4';

CREATE INDEX server_status_key_index ON server_status (`key`);

INSERT INTO server_status (`key`, value)
VALUES ('clip_size', '32768'),
       ('server_space', '8589934592'),
       ('space_usage', '0'),
       ('default_plan', '1')