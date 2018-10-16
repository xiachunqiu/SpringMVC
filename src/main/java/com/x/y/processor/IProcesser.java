package com.x.y.processor;

import org.quartz.Job;

public interface IProcesser extends Job {
    void process();
}