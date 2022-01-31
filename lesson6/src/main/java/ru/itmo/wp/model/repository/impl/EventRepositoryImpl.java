package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;
import java.sql.*;

public class EventRepositoryImpl extends BaseRepositoryImpl<Event> implements EventRepository {
    @Override
    protected String getObject() {
        return "Event";
    }

    @Override
    public void save(Event event) {
        save(event, new String[] {"userId", "type"}, new Object[]{event.getUserId(), event.getType()});
    }

    @Override
    protected void saveImpl(Event event, ResultSet generatedKeys) throws SQLException {
        event.setId(generatedKeys.getLong(1));
        event.setCreationTime(find(event.getId()).getCreationTime());
    }

    public Event find(long id) {
        return find(new String[] {"id"}, new Object[] {id});
    }

    @Override
    protected Event toObjectImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "type":
                    event.setType(resultSet.getString(i));
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getDate(i));
                    break;
                default:
                    // No operations.
            }
        }
        return event;
    }
}
