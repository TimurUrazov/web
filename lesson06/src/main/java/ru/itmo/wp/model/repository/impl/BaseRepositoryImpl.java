package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepositoryImpl<T> {
    protected final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    protected abstract String getObject();

    protected T find(String[] settersToString, Object[] setters) {
        return findImpl(findQueryBuilder(getObject(), settersToString), setters, settersToString);
    }

    String getError(String[] settersToString) {
        StringBuilder error = new StringBuilder("Can't find ");
        error.append(getObject()).append(" by");
        for (String setterToString : settersToString) {
            error.append(" ").append(setterToString);
        }
        error.append(".");
        return error.toString();
    }

    protected T findImpl(String query, Object[] setters, String[] settersToString) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                setInStatement(statement, setters);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toObject(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(getError(settersToString), e);
        }
    }

    private void setInStatement(PreparedStatement statement, Object[] setters) throws SQLException {
        for (int i = 1; i <= setters.length; i++) {
            if (setters[i - 1] instanceof String) {
                statement.setString(i, (String) setters[i - 1]);
            }
            if (setters[i - 1] instanceof Long) {
                statement.setLong(i, (Long) setters[i - 1]);
            }
        }
    }

    private String findQueryBuilder(String tableName, String[] selectors) {
        StringBuilder query = new StringBuilder(String.format("SELECT * FROM %s", tableName));
        query.append(" WHERE ");
        for (int i = 0; i < selectors.length; i++) {
            query.append(selectors[i]).append("=? ");
            if (i < selectors.length - 1) {
                query.append("AND ");
            }
        }
        return query.toString();
    }

    protected List<T> findAll(String query, Object... setters) {
        List<T> objects = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + getObject() + " " + query)) {
                setInStatement(statement, setters);
                try (ResultSet resultSet = statement.executeQuery()) {
                    T object;
                    while ((object = toObject(statement.getMetaData(), resultSet)) != null) {
                        objects.add(object);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find all " + getObject() + "s.", e);
        }
        return objects;
    }

    private String saveQueryBuilder(String[] selectors) {
        StringBuilder query = new StringBuilder("INSERT INTO `" + getObject() + "` (");
        for (String selector : selectors) {
            query.append("`").append(selector).append("`, ");
        }
        query.append("`creationTime`) VALUES (");
        for (int i = 0; i < selectors.length; i++) {
            query.append("?, ");
        }
        query.append("NOW())");
        return query.toString();
    }

    protected void save(T object, String[] query, Object[] setters) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(saveQueryBuilder(query), Statement.RETURN_GENERATED_KEYS)) {
                setInStatement(statement, setters);
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save " + getObject() + ".");
                } else {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        saveImpl(object, generatedKeys);
                    } else {
                        throw new RepositoryException("Can't save " + getObject() + " [no autogenerated fields].");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save " + getObject() + ".", e);
        }
    }

    protected abstract void saveImpl(T object, ResultSet generatedKeys) throws SQLException;

    private T toObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        return toObjectImpl(metaData, resultSet);
    }

    protected abstract T toObjectImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;
}
