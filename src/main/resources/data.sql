-- 'restaurant' 테이블 생성
CREATE TABLE IF NOT EXISTS restaurant(
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     restaurant_name VARCHAR(255),
     restaurant_number VARCHAR(255),
     restaurant_address VARCHAR(255),
     restaurant_money DOUBLE,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 'restaurant' 테이블에 초기 데이터 삽입
INSERT INTO restaurant (restaurant_name, restaurant_number, restaurant_address, restaurant_money, created_at, modified_at)
SELECT *, NOW(), NOW() FROM (
        SELECT '김밥천국' AS restaurant_name, '02-1234-5678' AS restaurant_number, '서울시 강남구' AS restaurant_address , 0 AS restaurant_money
--        UNION ALL
--        SELECT '야나두김밥', '02-9876-5432', '서울시 서초구'
--        UNION ALL
--        ELECT '어너두김밥', '02-1111-2222', '서울시 마포구'
    ) AS test
WHERE NOT EXISTS (
    SELECT 1 FROM restaurant
    WHERE restaurant.restaurant_name = test.restaurant_name
      AND restaurant.restaurant_number = test.restaurant_number
      AND restaurant.restaurant_address = test.restaurant_address
      AND restaurant.restaurant_money = test.restaurant_money
);

-- 'menu' 테이블 생성
CREATE TABLE IF NOT EXISTS menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255),
    name VARCHAR(255),
    price DOUBLE,
    restaurant_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

-- 'menu' 테이블에 초기 데이터 삽입
INSERT INTO menu (category, name, price, restaurant_id, created_at, modified_at)
SELECT *, (SELECT id FROM restaurant WHERE restaurant_name = '김밥천국'), NOW(), NOW() FROM (
      SELECT '김밥' AS category, '김밥' AS name, 2.0 AS price UNION ALL
      SELECT '김밥', '야채김밥', 3.0 UNION ALL
      SELECT '김밥', '참치김밥', 3.0 UNION ALL
      SELECT '라면', '라면', 2.0 UNION ALL
      SELECT '라면', '참치라면', 2.0 UNION ALL
      SELECT '라면', '해물라면', 2.0 UNION ALL
      SELECT '라면', '해장라면', 2.0
  ) AS init
WHERE NOT EXISTS (
    SELECT 1 FROM menu
    WHERE menu.category = init.category
      AND menu.name = init.name
      AND menu.price = init.price
);
