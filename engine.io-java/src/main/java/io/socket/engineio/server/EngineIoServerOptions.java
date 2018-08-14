package io.socket.engineio.server;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Options for {@link EngineIoServer}
 */
@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
public final class EngineIoServerOptions {

    /**
     * The default options used by server.
     * This instance is locked and cannot be modified.
     *
     * ping timeout: 5000
     * ping interval: 25000
     * allowed origins: *
     */
    public static final EngineIoServerOptions DEFAULT = new EngineIoServerOptions();

    /**
     * Specify that all origins are to be allowed for CORS
     */
    public static final String[] ALLOWED_CORS_ORIGIN_ALL = null;

    /**
     * Specify that no origins are allowed for CORS
     */
    public static final String[] ALLOWED_CORS_ORIGIN_NONE = new String[0];

    static {
        DEFAULT.setPingTimeout(5000);
        DEFAULT.setPingInterval(25000);
        DEFAULT.setAllowedCorsOrigins(ALLOWED_CORS_ORIGIN_ALL);
        DEFAULT.lock();
    }

    private boolean mIsLocked;
    private long mPingInterval;
    private long mPingTimeout;
    private String[] mAllowedCorsOrigins;

    private EngineIoServerOptions() {
        mIsLocked = false;
    }

    /**
     * Create a new instance of {@link EngineIoServerOptions} by copying
     * default options.
     *
     * @return New instance of {@link EngineIoServerOptions} with default options.
     */
    public static EngineIoServerOptions newFromDefault() {
        return (new EngineIoServerOptions())
                .setPingInterval(DEFAULT.getPingInterval())
                .setPingTimeout(DEFAULT.getPingTimeout())
                .setAllowedCorsOrigins(DEFAULT.getAllowedCorsOrigins());
    }

    /**
     * Gets the ping interval option.
     *
     * @return Ping interval in milliseconds.
     */
    public long getPingInterval() {
        return mPingInterval;
    }

    /**
     * Sets the ping interval option.
     *
     * @param pingInterval Ping interval in milliseconds.
     * @return Instance for chaining.
     * @throws IllegalStateException If instance is locked.
     */
    public EngineIoServerOptions setPingInterval(long pingInterval) throws IllegalStateException {
        if (mIsLocked) {
            throw new IllegalStateException("Ping interval cannot be set. Instance is locked.");
        }

        mPingInterval = pingInterval;
        return this;
    }

    /**
     * Gets the ping timeout option.
     *
     * @return Ping timeout in milliseconds.
     */
    public long getPingTimeout() {
        return mPingTimeout;
    }

    /**
     * Sets the ping timeout option.
     *
     * @param pingTimeout Ping timeout in milliseconds.
     * @return Instance for chaining.
     * @throws IllegalStateException If instance is locked.
     */
    public EngineIoServerOptions setPingTimeout(long pingTimeout) throws IllegalStateException {
        if (mIsLocked) {
            throw new IllegalStateException("Ping timeout cannot be set. Instance is locked.");
        }

        mPingTimeout = pingTimeout;
        return this;
    }

    /**
     * Gets the allowed cors origins option.
     *
     * @return Array of strings containing allowed cors origins.
     */
    public String[] getAllowedCorsOrigins() {
        return mAllowedCorsOrigins;
    }

    /**
     * Sets the allowed cord origins option.
     *
     * @param allowedCorsOrigins Array of strings containing allowed cors origins.
     * @return Instance for chaining.
     * @throws IllegalStateException If instance is locked.
     */
    public EngineIoServerOptions setAllowedCorsOrigins(String[] allowedCorsOrigins) throws IllegalStateException {
        if (mIsLocked) {
            throw new IllegalStateException("Allowed cors origins cannot be set. Instance is locked.");
        }

        mAllowedCorsOrigins = null;
        if (allowedCorsOrigins != null) {
            // Copy the array to prevent outside modifications
            mAllowedCorsOrigins = new String[allowedCorsOrigins.length];
            System.arraycopy(allowedCorsOrigins, 0, mAllowedCorsOrigins, 0, allowedCorsOrigins.length);

            // Sort the array for faster search on request
            Arrays.sort(mAllowedCorsOrigins, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
        }

        return this;
    }

    /**
     * Lock this options instance to prevent modifications.
     */
    public void lock() {
        mIsLocked = true;
    }
}