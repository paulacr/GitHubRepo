package net.paulacr.githubrepo.java;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.util.Log;

import net.paulacr.githubrepo.R;
import net.paulacr.githubrepo.assets.RepositoriesResponse200;
import net.paulacr.githubrepo.network.HttpConfig;
import net.paulacr.githubrepo.repositories.RepositoriesActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.mockwebserver.MockResponse;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by paularosa on 24/02/18.
 */
@RunWith(AndroidJUnit4.class)
public class RepositoriesTest extends InstrumentationTestCase {

    @Rule
    public ActivityTestRule<RepositoriesActivity> mActivityRule =
            new ActivityTestRule<>(RepositoriesActivity.class, true, false);

    @Rule
    public MockWebServerRule rule = new MockWebServerRule();

    @Before
    public void setUp() throws Exception {
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        HttpConfig.getInstance().setUrl(rule.server.url("/").toString());
    }

    @Test
    public void teste() throws Exception {

        rule.server.enqueue(new MockResponse().setResponseCode(200).setBody(getJsonOk()));

        mActivityRule.launchActivity(null);
        onView(ViewMatchers.withId(R.id.repoName))
                .check(matches(withText("texto")));
    }

    private String readJson() throws Exception {
        String file = "repositories.json";

        return RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), file);
    }

    private String getJsonOk() {
        return RepositoriesResponse200.json;
    }
}
