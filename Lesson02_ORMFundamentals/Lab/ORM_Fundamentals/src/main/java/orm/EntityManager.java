package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityManager<E> implements DatabaseContext<E> {
    private static final String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String UPDATE_WITH_WHERE_TEMPLATE = "UPDATE %s SET %s WHERE %s";
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
        Field idColumn = getIdColumn(entity);
        idColumn.setAccessible(true);
        Object idValue = idColumn.get(entity);


        if (idValue == null || (long) idValue == 0) {
            return doInsert(entity);
        }

        return doUpdate(entity, idColumn, idValue);
    }

    private boolean doUpdate(E entity, Field idColumn, Object idValue) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity);
        List<String> columns = getColumnsWithoutId(entity);
        List<String> values = getColumnValuesWithoutId(entity);

        List<String> columnsWithValues = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            String s = columns.get(i) + "=" + values.get(i);
            columnsWithValues.add(s);
        }

        String idCondition = String.format("%s=%s", idColumn.getName(), idValue.toString());

        String updateQuery = String.format(UPDATE_WITH_WHERE_TEMPLATE,
                tableName,
                String.join(",", columnsWithValues),
                idCondition);

        PreparedStatement statement = connection.prepareStatement(updateQuery);
        int updateCount = statement.executeUpdate();

        return updateCount == 1;
    }

    private boolean doInsert(E entity) throws IllegalAccessException, SQLException {
        // Generate INSERT
        // Get table name
        // Collect column without ID
        // Collect values without ID
        String tableName = getTableName(entity);

        List<String> columnsList = getColumnsWithoutId(entity);
        List<String> values = getColumnValuesWithoutId(entity);

        String formattedInsert = String.format(INSERT_TEMPLATE,
                tableName,
                String.join(",", columnsList),
                String.join(",", values));

        // Execute
        PreparedStatement statement = connection.prepareStatement(formattedInsert);
        int changedRows = statement.executeUpdate();

        // Parse result
        return changedRows == 1;
    }

    @Override
    public Iterable<E> find(Class<E> table) {
        return null;
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table, String where) {
        return null;
    }

    private Field getIdColumn(E entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                return declaredField;
            }
        }
        throw new RuntimeException("Entity has no Id column");
    }

    private String getTableName(E entity) {
        Entity annotation = entity.getClass().getAnnotation(Entity.class);

        if (annotation == null) {
            throw new RuntimeException("No Entity annotation present");
        }

        return annotation.name();
    }

    private List<String> getColumnsWithoutId(E entity) {
        List<String> result = new ArrayList<>();
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                continue;
            }
            Column column = declaredField.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }

            result.add(column.name());
        }

        return result;
    }

    private List<String> getColumnValuesWithoutId(E entity) throws IllegalAccessException {
        List<String> result = new ArrayList<>();

        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                continue;
            }
            if (!declaredField.isAnnotationPresent(Column.class)) {
                continue;
            }
            declaredField.setAccessible(true);
            Object fieldValue = declaredField.get(entity);

            result.add("'" + fieldValue.toString() + "'");
        }

        return result;
    }
}
