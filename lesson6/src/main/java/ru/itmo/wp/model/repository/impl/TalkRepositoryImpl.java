package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.repository.TalkRepository;
import java.sql.*;
import java.util.List;

public class TalkRepositoryImpl extends BaseRepositoryImpl<Talk> implements TalkRepository {
    @Override
    protected String getObject() {
        return "Talk";
    }

    @Override
    public void save(Talk talk) {
        save(talk, new String[] {"sourceUserId", "targetUserId", "text"}, new Object[]{talk.getSourceId(), talk.getTargetId(), talk.getMessage()});
    }

    @Override
    protected void saveImpl(Talk talk, ResultSet generatedKeys) throws SQLException {
        talk.setId(generatedKeys.getLong(1));
        talk.setCreationTime(find(talk.getId()).getCreationTime());
    }

    public Talk find(long id) {
        return find(new String[] {"id"}, new Object[] {id});
    }

    @Override
    public List<Talk> findAllById(long id) {
        return findAll("WHERE sourceUserId = ? OR targetUserId = ? ORDER BY creationTime", id, id);
    }

    @Override
    protected Talk toObjectImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setMessage(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }
        return talk;
    }
}
