# Spring Data Access (Spring Data) 
включает в себя аннотации и классы, которые упрощают доступ к данным и управление транзакциями.

## Аннотации

### @Repository

Обозначает класс как компонент доступа к данным. Он также включает обработку исключений, специфичных для базы данных, и является специальным видом @Component.
Пример:
```java
@Repository
public class UserRepository {
    // методы для доступа к данным
}
```
### @Transactional

Определяет, что методы или классы должны быть выполнены в рамках транзакции. Это позволяет управлять транзакциями, такие как их начало, коммит и откат.
Пример:
```java
@Service
public class UserService {
    @Transactional
    public void performTransaction() {
        // выполнение транзакционных операций
    }
}
```
### @Query

Используется в Spring Data JPA репозиториях для указания JPQL или SQL запроса. Позволяет выполнять запросы, которые не могут быть созданы автоматически.
Пример:

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);
}
```

### @Modifying

Используется вместе с @Query для указания, что запрос будет модифицировать данные (например, UPDATE или DELETE запрос).
Пример:

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("UPDATE User u SET u.active = false WHERE u.id = :id")
    void deactivateUser(@Param("id") Long id);
}
```

### @Entity

Обозначает класс как сущность JPA, которая будет отображаться на таблицу базы данных.
Пример:
```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    
    // геттеры и сеттеры
}
```

### @Table

Определяет имя таблицы в базе данных для сущности. Используется в сочетании с @Entity.
Пример:
```java
@Entity
@Table(name = "users")
public class User {
    // поля и методы
}
```

### @Column

Определяет имя и свойства столбца в таблице базы данных для поля сущности.
Пример:
```java
@Entity
public class User {
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    // геттеры и сеттеры
}
```

### @Id

Определяет поле сущности как идентификатор, который будет использоваться для уникальной идентификации записи.
Пример:
```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // другие поля
}

```

### @GeneratedValue

Определяет стратегию генерации значений для идентификатора сущности.
Пример:

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // другие поля
}

```

## Классы и интерфейсы

### JpaRepository

Расширяет PagingAndSortingRepository и предоставляет методы для работы с JPA, такие как поиск, сохранение и удаление сущностей.
Пример:
```java
public interface UserRepository extends JpaRepository<User, Long> {
    // дополнительные методы, если нужно
}
```

### CrudRepository
Базовый интерфейс для CRUD операций (Create, Read, Update, Delete).
Пример:

```java
public interface UserRepository extends CrudRepository<User, Long> {
    // дополнительные методы, если нужно
}
```

### PagingAndSortingRepository
Расширяет CrudRepository и добавляет поддержку пагинации и сортировки.
```java
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    // дополнительные методы, если нужно
}
```

### EntityManager
Основной интерфейс JPA для работы с контекстом персистентности. Позволяет выполнять операции над сущностями, такие как создание, обновление и удаление.
Пример:
```java
@PersistenceContext
private EntityManager entityManager;

```

### DataSource
Интерфейс, предоставляющий соединения с базой данных. Обычно настраивается в контексте Spring и используется для работы с JDBC.
Пример:
```java
@Autowired
private DataSource dataSource;

```