package org.fluentd.logger.reconnector;

import java.util.LinkedList;

import org.fluentd.logger.sender.SenderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use constant delay for reconnecting
 */
public class ConstantDelayReconnector implements Reconnector {
    private static final Logger LOG = LoggerFactory.getLogger(ConstantDelayReconnector.class);

    private double wait = 500; // Default wait to 500 ms

    private int maxErrorHistorySize = 100;

    private LinkedList<Long> errorHistory = new LinkedList<Long>();

    public ConstantDelayReconnector(int wait) {
        this.wait = wait;
        errorHistory = new LinkedList<Long>();
    }

    public void addErrorHistory(long timestamp) {
        errorHistory.addLast(timestamp);
        if (errorHistory.size() > maxErrorHistorySize) {
            errorHistory.removeFirst();
        }
    }

    public SenderStatus getSenderStatus() {
        // Return OK if the errorHistory is empty
        if (errorHistory.isEmpty()) {
            return SenderStatus.OK;
        }
        return SenderStatus.ERROR;

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

        boolean tryReconnectionBool = (!(nowTimestamp - errorHistory.getLast() < wait));
        LOG.warn(
                "The errorHistory is NOT empty; errorHistory.size():{}, tryReconnectionBool:{}, next reconnection in: {}ms",
                new Object[] { errorHistory.size(), tryReconnectionBool, (errorHistory.getLast() + wait) - nowTimestamp });
        return tryReconnectionBool;
    }
}