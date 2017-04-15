# dbcontract
Auto generates column names for entities

Annotate your model object with `@Entity` and an interface will be automatically generated for your model to implement. The inteface provides a static inner class with static final String fields representing the column names for the fields in the database.

```java
public class User implements UserDbContract {

    private long id;
    
    @StoredInDatabase(columnName = "someOtherName")
    private int someField;
    
    @NotStoredInDatabase
    private int fieldNotStored;
    
}
```

Generates:

```java
public interface UserDbContract {

    class Columns {
        static final String ID = "ID";
        static final String SOME_FIELD = "someOtherName";
    }
}
```

Now the fields are accessible by the model class:

```java
User.Columns.ID
```

The `@StoredInDatabase` and `@NotStoredInDatabase` annotations can be used on methods too, so, it can be used with AutoValue.

```java
public class User implements UserDbContract {
    
    // Methods need to be annotated for column names to be generated
    @StoredInDatabase
    public abstract int id();
    
    @StoredInDatabase(columnName = "someOtherName")
    public abstract int someField();
    
    @NotStoredInDatabase // Annotation is not needed but is nice to be explicit
    public abstract int fieldNotStored();
    
}
```

Note that this library is not an ORM and does not perform any database queries or manipulations. It simply auto-generates contract classes for your model fields to save time and maintenance.