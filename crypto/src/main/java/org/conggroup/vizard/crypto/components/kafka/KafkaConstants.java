package org.conggroup.vizard.crypto.components.kafka;

public class KafkaConstants {

    public static final String TOPIC_MESSAGE_RAW = "vizard.message.raw";
    public static final String TOPIC_MESSAGE_ENCRYPTED = "vizard.message.encrypted";

    public static final String TOPIC_POLICY_DPFKEY = "vizard.policy.dpfkey";
    public static final String TOPIC_POLICY_DPFKEY_REVOKE = "vizard.policy.dpfkey.revoke";

    public static final String TOPIC_BEAVER_DIFF = "vizard.beaver.diff";

    public static final String TOPIC_QUERY_REQUEST = "vizard.query.request";
    public static final String TOPIC_QUERY_RESULT = "vizard.query.result";

    public static final String BOOTSTRAP_SERVER_PREFIX = "vizard.kafka.bootstrap-server";
}
