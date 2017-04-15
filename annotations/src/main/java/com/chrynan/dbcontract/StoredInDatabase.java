package com.chrynan.dbcontract;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for fields or methods that should be stored in the database, therefore, represented in the contract.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface StoredInDatabase {

    /**
     * The column name for the annotated field or method. If this is not specified, an all caps version of the field or
     * method name (without get or set prefixes) with underscores separating the words (determined by uppercase letters
     * in the original name).
     *
     * @return The column name.
     */
    String columnName() default "";
}
