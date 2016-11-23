package com.thomaskioko.jkuat_codelab.api;


import com.thomaskioko.jkuat_codelab.util.ApplicationConstants;

import org.junit.BeforeClass;

/**
 * @author Thomas Kioko
 */
public abstract class BaseTestCase {

    private final static TmdbApiClient mTmdbApiClient = new TmdbApiClient();

    @BeforeClass
    public static void setUpOnce() {
        mTmdbApiClient.setIsDebug(ApplicationConstants.DEBUG);
    }

    /**
     * @return {@link TmdbApiClient} instance.
     */
    protected final TmdbApiClient getTmdbApiClient() {
        return mTmdbApiClient;
    }
}
