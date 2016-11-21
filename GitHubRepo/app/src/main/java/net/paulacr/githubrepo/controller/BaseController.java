package net.paulacr.githubrepo.controller;


import org.greenrobot.eventbus.EventBus;


class BaseController {

    public void post(Object event) {
        EventBus.getDefault().post(event);
    }
}
