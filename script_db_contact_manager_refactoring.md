# Рефакторинг базы данных contact_manager
### Создание новой таблицы phones:
```sql
CREATE TABLE phones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contact_id BIGINT NOT NULL,
    phone VARCHAR(64) NOT NULL,
    FOREIGN KEY (contact_id) REFERENCES contacts(id) ON DELETE CASCADE
);
```

### Перенос данных телефонов из таблицы contacts в новую таблицу phones:

```sql
INSERT INTO phones (contact_id, phone)
SELECT id, phone FROM contacts WHERE phone IS NOT NULL;
```
### Удаление столбца phone из таблицы contacts:

```sql
ALTER TABLE contacts DROP COLUMN phone;
```

### Обновление связанного кода приложения

### Добавление колонки ownerId в таблицу contacts:

```sql
ALTER TABLE contacts ADD COLUMN owner_id BIGINT;
```
### Обновление существующих данных
```sql
UPDATE contacts SET owner_id = 101 WHERE id = 1;
UPDATE contacts SET owner_id = 102 WHERE id = 2;
UPDATE contacts SET owner_id = 103 WHERE id = 3;
UPDATE contacts SET owner_id = 104 WHERE id = 4;
UPDATE contacts SET owner_id = 105 WHERE id = 5;
UPDATE contacts SET owner_id = 106 WHERE id = 6;
UPDATE contacts SET owner_id = 107 WHERE id = 7;
UPDATE contacts SET owner_id = 107 WHERE id = 7;
UPDATE contacts SET owner_id = 108 WHERE id = 8;
UPDATE contacts SET owner_id = 109 WHERE id = 9;
UPDATE contacts SET owner_id = 110 WHERE id = 10;
UPDATE contacts SET owner_id = 111 WHERE id = 11;
UPDATE contacts SET owner_id = 112 WHERE id = 12;
```

### Обновление таблицы contacts для поддержания внешнего ключа

```sql
ALTER TABLE contacts
ADD CONSTRAINT fk_owner
FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE SET NULL;

```