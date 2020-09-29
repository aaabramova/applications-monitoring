INSERT INTO users (id, username, password) VALUES (1, '1', '$2a$10$A8lKoZnCcEHJr1DRfBU1fOtH2fDA2Tf9dCjul.FiqlNaGO9rhO4m2');
INSERT INTO users (id, username, password) VALUES (2, '2', '$2a$10$A8lKoZnCcEHJr1DRfBU1fOtH2fDA2Tf9dCjul.FiqlNaGO9rhO4m2');
INSERT INTO users (id, username, password) VALUES (3, '3', '$2a$10$A8lKoZnCcEHJr1DRfBU1fOtH2fDA2Tf9dCjul.FiqlNaGO9rhO4m2');
INSERT INTO users (id, username, password) VALUES (4, '4', '$2a$10$A8lKoZnCcEHJr1DRfBU1fOtH2fDA2Tf9dCjul.FiqlNaGO9rhO4m2');

INSERT INTO user_roles (user_id, roles) VALUES (1, 'USER');
INSERT INTO user_roles (user_id, roles) VALUES (2, 'OPERATOR');
INSERT INTO user_roles (user_id, roles) VALUES (3, 'ADMIN');
INSERT INTO user_roles (user_id, roles) VALUES (4, 'USER');

INSERT INTO applications (date_of_creation, status, text, user_id) VALUES ('2020-09-29 14:46:13', 'SENT', 'annyeung', 1);
INSERT INTO applications (date_of_creation, status, text, user_id) VALUES ('2020-09-29 14:46:14', 'DRAFT', 'ni hao', 1);
INSERT INTO applications (date_of_creation, status, text, user_id) VALUES ('2020-09-29 14:46:15', 'DRAFT', 'hello', 1);
INSERT INTO applications (date_of_creation, status, text, user_id) VALUES ('2020-09-29 14:46:14', 'SENT', 'bonjour', 4);
INSERT INTO applications (date_of_creation, status, text, user_id) VALUES ('2020-09-29 14:46:17', 'SENT', 'hola', 4);