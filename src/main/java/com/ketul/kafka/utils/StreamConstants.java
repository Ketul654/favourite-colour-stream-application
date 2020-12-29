package com.ketul.kafka.utils;

public class StreamConstants {

    public static final String FAVOURITE_COLOUR_APPLICATION_ID = "favourite-colour-stream-application";
    public static final String BOOTSTRAP_SERVERS = "localhost:9092, localhost:9093, localhost:9094";
    public static final String FAVOURITE_COLOUR_INPUT_TOPIC = "favourite-colour-input";
    public static final String FAVOURITE_COLOUR_OUTPUT_TOPIC = "favourite-colour-output";
    public static final String AUTO_OFFSET_RESET_EARLIEST = "earliest";

    private StreamConstants(){}
}
