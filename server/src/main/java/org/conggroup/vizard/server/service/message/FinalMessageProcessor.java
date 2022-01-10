package org.conggroup.vizard.server.service.message;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.server.service.persistence.FinalMessagePersistenceUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinalMessageProcessor {

    private final FinalMessagePersistenceUtil finalMessagePersistenceUtil;

    public void process(VizardMessage message) {
        finalMessagePersistenceUtil.save(message);
    }
}
