# Script

```sql
DROP
DATABASE IF EXISTS contact_manager;
CREATE
DATABASE IF NOT EXISTS contact_manager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE
contact_manager;
CREATE TABLE contacts
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    fullname   VARCHAR(255),
    email      VARCHAR(64),
    phone      VARCHAR(64),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO contacts (name, fullname, email, phone)
VALUES ('Ekaterina', 'Beidel', 'ekaterina_beidel@gmai.com', '88002253555'),
       ('Ivan', 'Beidel', 'ivan_beidel@gmai.com', '88002253556'),
       ('Anna', 'Beidel', 'anna_beidel@gmai.com', '88002253557')
;
```

