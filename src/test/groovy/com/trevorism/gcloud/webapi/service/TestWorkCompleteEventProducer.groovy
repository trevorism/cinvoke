package com.trevorism.gcloud.webapi.service

import com.trevorism.event.WorkCompleteEventProducer
import com.trevorism.event.model.WorkComplete

/**
 * @author tbrooks
 */
class TestWorkCompleteEventProducer extends WorkCompleteEventProducer{

    boolean wasCalled = false

    void sendEvent(WorkComplete workComplete) {
        wasCalled = true
    }
}
