CREATE TABLE IF NOT EXISTS menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255),
    name VARCHAR(255),
    price double,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO menu (category, name, price, created_at, modified_at)
SELECT *, NOW() AS created_at, NOW() AS modified_at FROM (
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
