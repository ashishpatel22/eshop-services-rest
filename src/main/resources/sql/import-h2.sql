-- Entries in REGION table
INSERT INTO REGION (REGION_ID, DESCRIPTION)
VALUES ('NO', 'NO_DESC');
INSERT INTO REGION (REGION_ID, DESCRIPTION)
VALUES ('NE', 'NE_DESC');
INSERT INTO REGION (REGION_ID, DESCRIPTION)
VALUES ('EA', 'EA_DESC');
INSERT INTO REGION (REGION_ID, DESCRIPTION)
VALUES ('SE', 'SE_DESC');
INSERT INTO REGION (REGION_ID, DESCRIPTION)
VALUES ('SO', 'SO_DESC');
INSERT INTO REGION (REGION_ID, DESCRIPTION)
VALUES ('SW', 'SW_DESC');
INSERT INTO REGION (REGION_ID, DESCRIPTION)
VALUES ('WE', 'WE_DESC');
INSERT INTO REGION (REGION_ID, DESCRIPTION)
VALUES ('NW', 'NW_DESC');

-- Entries in Role table
INSERT INTO ROLE (role_id, role)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO ROLE (role_id, role)
VALUES (2, 'ROLE_USER');

-- Entries in user table
-- password in plaintext: "password" below value is encrypted one
INSERT INTO USER (user_id, password, email, username, active, CREATION_TIMESTAMP)
VALUES
  (1, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'ashish.patel22@gmail.com', 'ashish.patel', 1, CURRENT_TIMESTAMP);
-- password in plaintext: "password"
INSERT INTO USER (user_id, password, email, username, active, CREATION_TIMESTAMP)
VALUES
  (2, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'testadmin@email.com', 'testadmin', 1, CURRENT_TIMESTAMP);
-- password in plaintext: "password"
INSERT INTO USER (user_id, password, email, username, active, CREATION_TIMESTAMP)
VALUES (3, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'customer1@email.com', 'customer1', 1, CURRENT_TIMESTAMP);
-- password in plaintext: "password"
INSERT INTO USER (user_id, password, email, username, active, CREATION_TIMESTAMP)
VALUES (4, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'customer2@email.com', 'customer2', 1, CURRENT_TIMESTAMP);

-- INSERT values into CUSTOEMR table
INSERT INTO CUSTOMER (CUSTOMER_ID, ADDRESS, FIRST_NAME, LAST_NAME, REGION_ID, USER_ID, CREATION_TIMESTAMP)
VALUES
    ('CUSTID_1', 'F902 Amanora Hadapsar Pune 411028', 'Ashish', 'Patel', 'NO', 1, CURRENT_TIMESTAMP);
INSERT INTO CUSTOMER (CUSTOMER_ID, ADDRESS, FIRST_NAME, LAST_NAME, REGION_ID, USER_ID, CREATION_TIMESTAMP)
VALUES
    ('CUSTID_2', 'L604 Magarpatta Hadapsar Pune 411028', 'Admin', 'Root', 'NE', 2, CURRENT_TIMESTAMP);
INSERT INTO CUSTOMER (CUSTOMER_ID, ADDRESS, FIRST_NAME, LAST_NAME, REGION_ID, USER_ID, CREATION_TIMESTAMP)
VALUES
    ('CUSTID_3', 'M604 Magarpatta Hadapsar Pune 411028', 'Customer', 'One', 'NO', 3, CURRENT_TIMESTAMP);
    INSERT INTO CUSTOMER (CUSTOMER_ID, ADDRESS, FIRST_NAME, LAST_NAME, REGION_ID, USER_ID, CREATION_TIMESTAMP)
VALUES
    ('CUSTID_4', 'K604 Magarpatta Hadapsar Pune 411028', 'Customer', 'Two', 'NE', 4, CURRENT_TIMESTAMP);

-- Entries in User_Role table Many to many mapping
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 2);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (2, 1);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (2, 2);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (3, 2);

-- Entries in Produt table
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (1, 'I-phone4', 'Apple I-phone 4', 1, 440.75, 'NE');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (2, 'I-phone5', 'Apple I-phone 5', 15, 540.40, 'NE');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (3, 'I-phone6', 'Apple I-phone 6', 25, 640.40, 'NE');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (4, 'I-phone7', 'Apple I-phone 7', 45, 740.40, 'NE');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (5, 'I-phone8', 'Apple I-phone 8', 55, 840.40, 'NE');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (6, 'I-phone9', 'Apple I-phone 9', 65, 940.40, 'NE');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (7, 'I-phone10', 'Apple I-phone 10', 5, 1040.40, 'NE');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (8, 'I-phone11', 'Apple I-phone 11', 35, 1140.40, 'NE');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (9, 'I-phone12', 'Apple I-phone 12', 85, 1240.40, 'NE');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (10, 'I-phone14', 'Apple I-phone 14', 95, 1440.40, 'NE');

INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (11, 'I-phone4', 'Apple I-phone 4', 11, 440.75, 'NO');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (12, 'I-phone5', 'Apple I-phone 5', 115, 540.40, 'NO');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (13, 'I-phone6', 'Apple I-phone 6', 125, 640.40, 'NO');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (14, 'I-phone7', 'Apple I-phone 7', 145, 740.40, 'NO');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (15, 'I-phone8', 'Apple I-phone 8', 155, 840.40, 'NO');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (16, 'I-phone9', 'Apple I-phone 9', 165, 940.40, 'NO');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (17, 'I-phone10', 'Apple I-phone 10', 15, 1040.40, 'NO');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (18, 'I-phone11', 'Apple I-phone 11', 135, 1140.40, 'NO');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (19, 'I-phone12', 'Apple I-phone 12', 185, 1240.40, 'NO');
INSERT INTO PRODUCT (product_id, name, description, quantity_available, price, region_id)
VALUES (20, 'I-phone14', 'Apple I-phone 14', 195, 1440.40, 'NO');