package com.chryan.dbcontract;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.util.Map;

import javax.lang.model.element.Modifier;

/**
 * A class that creates a database contract interface Java Source File, represented as a {@link JavaFile}, for a
 * provided {@link EntityClass}.
 */
class ContractClassCreator {

    private static final String CLASS_NAME_SUFFIX = "DbContract";
    private static final String TABLE_FIELD_NAME = "TABLE_NAME";
    private static final String COLUMN_CLASS_NAME = "Columns";

    static JavaFile create(EntityClass entityClass) {
        TypeSpec.Builder contractInterface = TypeSpec.interfaceBuilder(entityClass.getTypeElement().getSimpleName().toString() + CLASS_NAME_SUFFIX)
                .addModifiers(Modifier.PUBLIC)
                .addField(FieldSpec.builder(String.class, TABLE_FIELD_NAME)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("$S", entityClass.getTableName())
                        .build());

        TypeSpec.Builder columnClass = TypeSpec.classBuilder(COLUMN_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);

        for (Map.Entry<String, String> column : entityClass.getColumnNames().entrySet()) {
            columnClass.addField(FieldSpec.builder(String.class, column.getKey())
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", column.getValue())
                    .build());
        }

        contractInterface.addType(columnClass.build());

        return JavaFile.builder(entityClass.getPackageName(), contractInterface.build()).build();
    }
}
