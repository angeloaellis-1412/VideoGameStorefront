CREATE DATABASE IF NOT EXISTS video_game_storefront;

USE video_game_storefront;

CREATE TABLE games (
    id BIGINT NOT NULL AUTO_INCREMENT,
    active BIT NOT NULL,
    description VARCHAR(255),
    genre VARCHAR(255) NOT NULL,
    platform VARCHAR(255) NOT NULL,
    price DECIMAL(38,2) NOT NULL,
    release_year INT NOT NULL,
    stock_quantity INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CHECK (stock_quantity >= 0)
);

CREATE TABLE orders (
    id BIGINT NOT NULL AUTO_INCREMENT,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    order_date DATETIME(6),
    phone VARCHAR(255),
    total_amount DECIMAL(38,2),
    zip VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE order_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    price DECIMAL(38,2),
    quantity INT NOT NULL,
    game_id BIGINT,
    order_id BIGINT,
    PRIMARY KEY (id),
    CHECK (quantity >= 1),
    CONSTRAINT fk_order_items_game
        FOREIGN KEY (game_id)
        REFERENCES games(id),
    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
);