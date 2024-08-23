# Script

```sql
USE
contact_manager;
CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(50),
    name       VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO users (email, password, role, name)
VALUES ('john.doe@example.com', 'password123', 'admin', 'John Doe'),
       ('jane.smith@example.com', 'password456', 'admin', 'Jane Smith'),
       ('alice.johnson@example.com', 'password789', 'admin', 'Alice Johnson'),
       ('bob.brown@example.com', 'password321', 'admin', 'Bob Brown'),
       ('carol.white@example.com', 'password654', 'admin', 'Carol White');
```
