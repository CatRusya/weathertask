INSERT INTO users (id, name, username, password)
VALUES (1, 'John Doe', 'johndoe@gmail.com', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W'),
       (2, 'Mike Smith', 'mikesmith@yahoo.com', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m');

INSERT INTO user_role (user_id, role)
VALUES (1, 'ROLE_EDITOR'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');


