package com.x.y.processor;

import org.quartz.Job;

public interface IProcessor extends Job {
    void process();
}