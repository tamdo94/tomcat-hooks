package com.tamaws.tomcat.listener;

import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomTomcatListener implements LifecycleListener {

    private static final Logger LOG = Logger.getLogger(CustomTomcatListener.class.getName());
    /**
     * All the events of tomcat
     * AFTER_START_EVENT,
     * AFTER_STOP_EVENT,
     * BEFORE_START_EVENT,
     * BEFORE_STOP_EVENT,
     * DESTROY_EVENT,
     * INIT_EVENT,
     * PERIODIC_EVENT,
     * START_EVENT,
     * STOP_EVENT
     */
    private static int counter;

    @Override
    public void lifecycleEvent(LifecycleEvent lifecycleEvent) {

        try {
            String event = lifecycleEvent.getType();
            LOG.log(Level.INFO,"Tomcat Events: " + (++counter) + " :: " + event);
            if(event.equals("periodic")) {
                LOG.log(Level.INFO, "Hey I've started");

                URL url = new URL("http://localhost:8080/web-app/hello");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                int status = con.getResponseCode();
                LOG.log(Level.INFO, "Call activate endpoint status: " + status);

                lifecycleEvent.getLifecycle().removeLifecycleListener(this);
            }
        } catch (Exception e) {
            LOG.log(Level.INFO, "Event handler has failed" + e.getMessage());
        }
    }
}
