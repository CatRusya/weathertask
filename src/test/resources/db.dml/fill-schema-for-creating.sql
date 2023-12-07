INSERT INTO users (id, name, username, password)
VALUES (1, 'John Doe', 'johndoe@gmail.com', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W'),
       (2, 'Mike Smith', 'mikesmith@yahoo.com', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m');

INSERT INTO weather (id, date, lat, lon, city, state)
VALUES (1,'2023-12-03', 40.7128, -74.0060, 'New York', 'NY'),
       (2,'2023-12-04', 34.0522, -118.2437, 'Los Angeles', 'CA'),
       (3,'2023-12-05', 41.8781, -87.6298, 'Chicago', 'IL'),
       (4,'2023-12-06', 29.7604, -95.3698, 'Houston', 'TX'),
       (5,'2023-12-07', 33.4484, -112.0740, 'Phoenix', 'AZ');

INSERT INTO temperature (weather_id, "value")
VALUES(1, 25.5),
      (1, 26.0),
      (2, 20.5),
      (2, 21.0),
      (3, 15.5),
      (3, 16.0),
      (4, 28.5),
      (4, 29.0),
      (5, 18.5),
      (5, 19.0);


INSERT INTO user_role (user_id, role)
VALUES (1, 'ROLE_EDITOR'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');


