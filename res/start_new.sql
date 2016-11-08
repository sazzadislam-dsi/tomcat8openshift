INSERT INTO Organization (name) VALUES ('Nahid');
INSERT INTO Organization (name) VALUES ('sazzad');


INSERT INTO AppUser (authorities, password, username, organization_id) VALUES ('ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_MANAGER', '$2a$10$HAChunMYFtX6tdlfBlvJBOp8ognLbsVTEG.70M5IUNJ0CWwo5t16.', 'sazzad', 1);




INSERT INTO Book (name, organization_id) VALUES ('Faridpur', 1);
INSERT INTO Book (name, organization_id) VALUES ('Dhaka', 1);
INSERT INTO Book (name, organization_id) VALUES ('Lalbag', 2);




INSERT INTO Account (name, book_id) VALUES ('Sazzad', 1);
INSERT INTO Account (name, book_id) VALUES ('Shahad', 1);
INSERT INTO Account (name, book_id) VALUES ('Maruf', 1);
INSERT INTO Account (name, book_id) VALUES ('Lopa', 2);
INSERT INTO Account (name, book_id) VALUES ('Niloy', 2);




INSERT INTO AccountTransaction (amount, colorType, description, entryDate, entryType, voucherNumber, account_id) VALUES (11, 'white', 'desc', '2016-03-28 08:43:59', 'DEBIT', '1', 1);
INSERT INTO AccountTransaction (amount, colorType, description, entryDate, entryType, voucherNumber, account_id) VALUES (100, 'white', 'desc', '2016-03-28 09:43:59', 'DEBIT', '1', 1);
INSERT INTO AccountTransaction (amount, colorType, description, entryDate, entryType, voucherNumber, account_id) VALUES (50, 'white', 'desc', '2016-03-28 10:43:59', 'DEBIT', '1', 5);
INSERT INTO AccountTransaction (amount, colorType, description, entryDate, entryType, voucherNumber, account_id) VALUES (11, 'white', 'des222c', '2016-03-28 08:43:59', 'DEBIT', '222', 1);
INSERT INTO AccountTransaction (amount, colorType, description, entryDate, entryType, voucherNumber, account_id) VALUES (11, 'white', 'd333', '2016-03-29 06:00:00', 'DEBIT', '952', 1);
INSERT INTO AccountTransaction (amount, colorType, description, entryDate, entryType, voucherNumber, account_id) VALUES (500, 'white', 'descr', '2016-03-29 06:00:00', 'DEBIT', '952', 1);
INSERT INTO AccountTransaction (amount, colorType, description, entryDate, entryType, voucherNumber, account_id) VALUES (50, 'white', 'descr', '2016-03-29 06:00:00', 'CREDIT', '362', 1);



