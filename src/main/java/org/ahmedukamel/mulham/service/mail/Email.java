package org.ahmedukamel.mulham.service.mail;

import java.util.Map;

public interface Email {
    String getReceiver();

    Map<String, ?> getVariables();
}