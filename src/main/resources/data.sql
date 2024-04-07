DELETE FROM tb_beer_order;
DELETE FROM tb_customer;
INSERT INTO tb_customer (id, name, api_key, created_at, updated_at, version ) values ('0a818933-087d-47f2-ad83-2f986ed087eb', 'Tasting Room', '8aeb6dad-1d56-4769-8de9-0dae263d5bb9', CURRENT_TIMESTAMP , CURRENT_TIMESTAMP , 1);
INSERT INTO tb_beer_order (id, created_at, customer_reference, order_status, order_status_callback_url, updated_at, version, customer_id) VALUES('97e1aef4-1c90-471a-8290-ef4df6237a60', null, null, null, null, null, 0, '0a818933-087d-47f2-ad83-2f986ed087eb');