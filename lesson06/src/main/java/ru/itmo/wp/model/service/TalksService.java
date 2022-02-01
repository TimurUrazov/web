package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TalksService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public void validateText(String text) throws ValidationException {
        if (Strings.isNullOrEmpty(text)) {
            throw new ValidationException("Message should contain at least one symbol");
        }
        if (text.length() > 255) {
            throw new ValidationException("Too long message");
        }
    }

    public void saveMessage(User source, User target, String text) {
        Talk talk = new Talk();
        talk.setSourceId(source.getId());
        talk.setTargetId(target.getId());
        talk.setMessage(text);

        talkRepository.save(talk);
    }

    public List<TalkView> findAllById(long id) {
        List<TalkView> talks = new ArrayList<>();

        for (Talk talk : talkRepository.findAllById(id)) {
            talks.add(new TalkView(userRepository.find(talk.getSourceId()),
                    userRepository.find(talk.getTargetId()), talk.getMessage(), talk.getCreationTime()));
        }

        return talks;
    }

    public static class TalkView {
        private final User source;
        private final User target;
        private final String text;
        private final Date date;

        public TalkView(User source, User target, String text, Date date) {
            this.source = source;
            this.target = target;
            this.text = text;
            this.date = date;
        }

        public Date getDate() {
            return date;
        }

        public User getSource() {
            return source;
        }

        public User getTarget() {
            return target;
        }

        public String getText() {
            return text;
        }
    }
}
