package com.chryan.dbcontract;

import com.chrynan.dbcontract.Entity;
import com.chrynan.dbcontract.NotStoredInDatabase;
import com.chrynan.dbcontract.StoredInDatabase;
import com.squareup.javapoet.ClassName;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Represents a class annotated with the {@link Entity} annotation providing useful fields for the interface contract
 * class creation.
 */
class EntityClass {

    private String packageName;
    private TypeElement typeElement;
    private ClassName className;
    private String tableName;
    private Map<String, String> columnNames;

    EntityClass(TypeElement element) {
        this.typeElement = element;
        this.className = ClassName.get(element);

        this.packageName = className.packageName();

        Entity entity = element.getAnnotation(Entity.class);

        this.tableName = entity.tableName().equals("") ? StringUtils.getColumnName(typeElement.getSimpleName().toString()) : entity.tableName();

        this.columnNames = getColumnNames(typeElement);
    }

    String getPackageName() {
        return packageName;
    }

    TypeElement getTypeElement() {
        return typeElement;
    }

    String getTableName() {
        return tableName;
    }

    Map<String, String> getColumnNames() {
        return columnNames;
    }

    private Map<String, String> getColumnNames(TypeElement typeElement) {
        Map<String, String> names = new HashMap<>();

        for (Element element : typeElement.getEnclosedElements()) {
            if (element.getAnnotation(NotStoredInDatabase.class) != null) {
                continue;
            }

            String simpleName = element.getSimpleName().toString();

            StoredInDatabase stored = element.getAnnotation(StoredInDatabase.class);

            if (stored != null && !StringUtils.isEmpty(stored.columnName())) {
                names.put(StringUtils.getColumnFieldName(stored.columnName()), stored.columnName());
            } else if (stored != null && element.getKind() == ElementKind.METHOD) {
                String n = StringUtils.isEmpty(stored.columnName()) ? simpleName : stored.columnName();

                names.put(StringUtils.getColumnFieldNameFromMethodName(n), StringUtils.getColumnNameFromMethodName(n));
            } else if (element.getKind() == ElementKind.FIELD && !element.getModifiers().contains(Modifier.STATIC)) {
                names.put(StringUtils.getColumnFieldName(simpleName), StringUtils.getColumnName(simpleName));
            }
        }

        return names;
    }
}
