-- Store 더미 데이터
INSERT INTO namu.store (
    store_name, store_category, store_address, store_picture_urls, store_phone, notice, store_content,
    pickup_time, closed_days, store_rating, favorite_count, review_count, order_count, ceo_name, company_name,
    company_address, business_number, country_of_origin, is_open, min_price, location, status, create_at, update_at
)
VALUES
    (
        '요거피플 동래점', 'DESSERT', '부산광역시 동래구 수안동 188-6 2층 5호(수안동)', NULL, '0507-1234-1234',
        '요거트 아이스크림 요거피플~\n기본 보냉팩 포장!!\n요거피플 동래점은 부산에서 양 젤 많은 무지방 요거트 아이스크림입니당\n우유는 국내 1등 서울우유 무지방만 취급합니다.\n당일 경매 받은 신선한 과일만 취급합니다:):)',
        '요거트 아이스크림 요거피플~\n기본 보냉팩 포장!!\n요거피플 동래점은 부산에서 양 젤 많은 무지방 요거트 아이스크림입니당\n우유는 국내 1등 서울우유 무지방만 취급합니다.\n당일 경매 받은 신선한 과일만 취급합니다:):)',
        '19:00~22:00', NULL, 4.0, 12, 3, 20, '박준성', '요거피플 동래점', '부산광역시 동래구 수안동 188-6 2층 5호(수안동)',
        '763-17-02432', '요거트 가루(네덜란드산), 우유(국내산), 벌집꿀(국내산)', true, 0, 10, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    ),
    (
        '카페 그라운드', 'DESSERT', '부산광역시 금정구 장전동 409-26(장전동)',
        '["https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20210622_125%2F1624370007904VpQo3_JPEG%2F2Bl08VFne2DIdmB0UoCmIn1I.jpg"]',
        '0507-1388-8812', NULL, NULL, '10:00~20:00', NULL, 4.9, 435, 356, 400, '정영우', '카페 그라운드',
        '부산광역시 금정구 장전동 409-26 1,2층(장전동)', '316-29-00793', '원두: 수입산 / 우유: 국산 / 버터: 프랑스산 / 크림: 프랑스산 / 밀가루: 프랑스산 / 초콜렛: 프랑스산, 벨기에산 / 딸기: 국산 / 그 외 과일: 수입산 / 녹차: 국산', true, 0, 10, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    ),
    (
        '모리커피 부산온천장점', 'DESSERT', '부산 동래구 온천장로65번길 9 111호(온천동, 동래 3차 SK VIEW)',
        '["https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220607_148%2F1654598545170y2HXp_JPEG%2F1654598503408.jpg",
          "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220530_5%2F1653842866421H0YQ0_JPEG%2F20220529_192000.jpg",
          "https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20220530_112%2F1653842894162zHENE_JPEG%2F20220529_190338.jpg"]',
        '0507-1444-3053', NULL, '냉율무가 레전드 최고로 인기 많은 모리커피\n2024년 8월20일! 모리커피 사장님이 바뀌었습니다!\n트렌디하고 손맛좋은 사장님이 직접 풀타임 근무하며 제조하고 있습니다!\n많은 응원과 관심 부탁드립니다!\n\n아실분들은 다 아시는 저희 모리커피 시그니처 메뉴!\n-냉율무\n-딸기라떼\n-모리비엔나\n-오렌지자몽주스\n-율무커피(라떼)\n\n사장님의 소신있는 리뉴얼 디저트 추천!><\n-모리치즈빵(계란빵)\n-바질햄치즈치아바타(바질 가득가득 매일먹어요ㅠㅠ)\n-누들떡볶이(어떻게 식어도 맛있지?)\n-볶음밥3종 (지인짜 마싯어요ㅠㅠㅠ)\n-팝콘치킨(사장이 먹을려고 메뉴 넣은거 진짜아님!',
        '12:00~22:00', NULL, 5.0, 172, 200, 250, '정은영', '모리커피 부산온천장점',
        '부산 동래구 온천장로65번길 9 111호(온천동, 동래 3차 SK VIEW)', '603-25-73135', '원두 : 아라비카100% (콜롬비아,과테말라,브라질) 우유:서울우유(국산원유100%)', true, 0, 10, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
    );


-- 메뉴 더미 데이터
INSERT INTO namu.menu (store_id, set_name, menu_names, menu_price, menu_discount_price, menu_picture_url, popularity, menu_detail, menu_category, status, create_at, update_at)
VALUES (1, '세트A', '애플 와플(1), 콘치폭 핫도그(1)', 10000, 7000, 'https://example.com/image1.jpg', true, '애플 와플과 콘치폭 핫도그 세트', 'DESSERT', 'ON_SALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO namu.menu (store_id, set_name, menu_names, menu_price, menu_discount_price, menu_picture_url, popularity, menu_detail, menu_category, status,  create_at, update_at)
VALUES (2, '세트B', '치킨 샌드위치(1), 콜라(1)', 8000, 6000, 'https://example.com/image2.jpg', false, '치킨 샌드위치와 콜라 세트', 'SANDWICHE', 'PREPARING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO namu.menu (store_id, set_name, menu_names, menu_price, menu_discount_price, menu_picture_url, popularity, menu_detail, menu_category, status,  create_at, update_at)
VALUES (3, '세트C', '피자(1), 스파게티(1)', 15000, 12000, 'https://example.com/image3.jpg', true, '피자와 스파게티 세트', 'MART', 'SOLD_OUT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);