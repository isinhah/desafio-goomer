CREATE TABLE TB_RESTAURANTS (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    opening_hours VARCHAR(255) NOT NULL
);

CREATE TABLE TB_PRODUCTS (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    category VARCHAR(255) NOT NULL,
    promotional_description TEXT,
    promotional_price DECIMAL(10, 2),
    promotional_days TEXT,
    promotional_hours VARCHAR(255),
    is_on_promotion BOOLEAN,
    restaurant_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES TB_RESTAURANTS(id) ON DELETE CASCADE
);
