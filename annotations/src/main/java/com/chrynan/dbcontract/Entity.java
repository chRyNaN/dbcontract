package com.chrynan.dbcontract;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation for model classes that want to have a database model contract generated for it's fields.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Entity {

    /**
     * The table name for the annotated model class. If this is not specified, an all caps version of the class name
     * (without get or set prefixes) with underscores separating the words (determined by uppercase letters in the
     * original name).
     *
     * @return The table name.
     */
    String tableName() default "";
}
