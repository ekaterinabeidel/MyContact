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

### Обновление связанного кода приложения:

```sql
SELECT c.name, p.phone
FROM contacts c
LEFT JOIN phones p ON c.id = p.contact_id
WHERE c.id = 1;
```