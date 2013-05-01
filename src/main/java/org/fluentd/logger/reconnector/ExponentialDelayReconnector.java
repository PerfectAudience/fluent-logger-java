package org.fluentd.logger.reconnector;

import java.util.LinkedList;

import org.fluentd.logger.sender.RawSocketSender;
import org.fluentd.logger.sender.SenderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Calculate exponential delay for reconnecting
 */
public class ExponentialDelayReconnector implements Reconnector {
    private static final Logger LOG = LoggerFactory.getLogger(RawSocketSender.class);

    private double wait = 0.5;

    private double waitIncrRate = 1.5;

    private double waitMax = 60;

    private int waitMaxCount;

    private LinkedList<Long> errorHistory;

    public ExponentialDelayReconnector() {
        waitMaxCount = getWaitMaxCount();
        errorHistory = new LinkedList<Long>();
    }

    private int getWaitMaxCount() {
        double r = waitMax / wait;
        for (int j = 1; j <= 100; j++) {
            if (r < waitIncrRate) {
                return j + 1;
            }
            r = r / waitIncrRate;
        }
        return 100;
    }

    public void addErrorHistory(long timestamp) {
        errorHistory.addLast(timestamp);
        if (errorHistory.size() > waitMaxCount) {
            errorHistory.removeFirst();
        }
    }

    public void clearErrorHistory() {
        errorHistory.clear();
    }

    public boolean enableReconnection() {
        long nowTimestamp = System.currentTimeMillis();
        int size = errorHistory.size();
        if (getSenderStatus().equals(SenderStatus.OK)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("The senderStatus is OK; return true");
            }
            return true;
        }

        double suppressSec;
        if (size < waitMaxCount) {
            suppressSec = wait * Math.pow(waitIncrRate, size - 1);
        } else {
            suppressSec = waitMax;
        }
        boolean tryReconnectionBool = (!(nowTimestamp - errorHistory.getLast() < suppressSec));
        LOG.warn(
                "The errorHistory is NOT empty; errorHistory.size():{}, tryReconnectionBool:{}, next reconnection in: {}ms",
                new Object[] { errorHistory.size(), tryReconnectionBool,
                        (errorHistory.getLast() + suppressSec) - nowTimestamp });
        return tryReconnectionBool;
    }

    @Override
    public SenderStatus getSenderStatus() {
        // Return OK if the errorHistory is empty
        if (errorHistory.isEmpty()) {
            return SenderStatus.OK;
        }
        return SenderStatus.ERROR;

    }
}
