package com.chrynan.dbcontract;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for fields or methods that should not be stored in the database, therefore, not represented in the
 * contract.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface NotStoredInDatabase {
}
