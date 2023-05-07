CREATE TABLE IF NOT EXISTS texts
(
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(255) NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    data_text   VARCHAR(255) NOT NULL
);
