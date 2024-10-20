-- Criação da tabela de Restaurantes
CREATE TABLE tb_restaurants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    address VARCHAR(255) NOT NULL,
    opening_hours VARCHAR(255) NOT NULL
);

-- Criação da tabela de Produtos
CREATE TABLE tb_products (
    id VARCHAR(36) NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL,
    image_url VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    category VARCHAR(255) NOT NULL,
    promotional_description TEXT,
    promotional_price DECIMAL(10, 2),
    promotional_days VARCHAR(255),
    promotion_hours VARCHAR(255),
    is_on_promotion BOOLEAN,
    restaurant_id BIGINT NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES tb_restaurants(id) ON DELETE CASCADE
);

-- Inserir dados na tabela tb_restaurants
INSERT INTO tb_restaurants (name, image_url, description, opening_hours)
VALUES
    ('Sushi Paradise', 'http://example.com/sushi_paradise.jpg', 'Fresh and delicious sushi.', 'Monday to Friday: 12:00 - 22:00, Saturday: 13:00 - 23:00'),
    ('Pizza Haven', 'http://example.com/pizza_haven.jpg', 'Tasty pizzas and more.', 'Monday to Friday: 12:00 - 22:00, Saturday: 13:00 - 23:00');
