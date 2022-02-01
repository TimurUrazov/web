package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;

public class EventService {
    private final EventRepository eventRepository = new EventRepositoryImpl();

    public void recordEvent(User user, boolean enter) {
        Event event = new Event();
        event.setType(enter ? "ENTER" : "LOGOUT");
        event.setUserId(user.getId());
        eventRepository.save(event);
    }
}
