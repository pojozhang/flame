package io.bayberry.repository.event;

import org.springframework.data.mongodb.core.query.Update;

public class AfterUpdateAppliedEvent extends RepositoryEvent<Update> {

    public AfterUpdateAppliedEvent(Update source) {
        super(source);
    }
}