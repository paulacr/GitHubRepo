package net.paulacr.githubrepo.base;


import org.greenrobot.eventbus.EventBus;


public class BaseController {

    public void post(Object event) {
        EventBus.getDefault().post(event);
    }
}
