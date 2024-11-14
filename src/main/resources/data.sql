-- 메뉴 더미 데이터
INSERT INTO menu (set_name, menu_names, menu_price, menu_discount_price, menu_picture_url, popularity, menu_detail, menu_category, status)
VALUES ('세트A', '애플 와플(1), 콘치폭 핫도그(1)', 10000, 7000, 'https://example.com/image1.jpg', true, '애플 와플과 콘치폭 핫도그 세트', '디저트', 'ON_SALE');

INSERT INTO menu (set_name, menu_names, menu_price, menu_discount_price, menu_picture_url, popularity, menu_detail, menu_category, status)
VALUES ('세트B', '치킨 샌드위치(1), 콜라(1)', 8000, 6000, 'https://example.com/image2.jpg', false, '치킨 샌드위치와 콜라 세트', '패스트푸드', 'PREPARING');

INSERT INTO menu (set_name, menu_names, menu_price, menu_discount_price, menu_picture_url, popularity, menu_detail, menu_category, status)
VALUES ('세트C', '피자(1), 스파게티(1)', 15000, 12000, 'https://example.com/image3.jpg', true, '피자와 스파게티 세트', '이탈리안', 'SOLD_OUT');