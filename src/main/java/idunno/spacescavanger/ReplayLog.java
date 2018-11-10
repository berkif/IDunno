package idunno.spacescavanger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ReplayLog {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReplayLog.class);

    public static void append(String jsonData) {
        LOGGER.trace(jsonData);
    }
}
