CREATE TABLE users
(
    user_id    BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
    created_at DATETIME NULL,
    updated_at DATETIME NULL,
    password   VARCHAR(255) NULL,
    name       VARCHAR(255) NULL,
    picture    VARCHAR(255) NULL,
    username   VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    enabled    BIT(1)       NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id),
    CONSTRAINT uc_users_email UNIQUE (email),
    CONSTRAINT uc_users_username UNIQUE (username)
);

CREATE TABLE cards
(
    card_id    BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
    created_at DATETIME NULL,
    updated_at DATETIME NULL,
    name       VARCHAR(255) NULL,
    user_id    BIGINT NOT NULL,
    card_type  SMALLINT NULL,
    amount     DECIMAL NULL,
    CONSTRAINT pk_cards PRIMARY KEY (card_id, user_id),
    CONSTRAINT FK_CARDS_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE transactions
(
    id              BIGINT NOT NULL AUTO_INCREMENT,
    created_at      DATETIME NULL,
    updated_at      DATETIME NULL,
    card_id         BIGINT NOT NULL,
    type            SMALLINT NULL,
    amount          DECIMAL NULL,
    initial_balance DECIMAL NULL,
    final_balance   DECIMAL NULL,
    note            VARCHAR(255) NULL,
    `description`   VARCHAR(255) NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (id, card_id),
    CONSTRAINT FK_TRANSACTIONS_ON_CARD FOREIGN KEY (card_id) REFERENCES cards (card_id)
);