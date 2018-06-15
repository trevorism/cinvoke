package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.CreateJenkinsJob
import com.trevorism.gcloud.webapi.service.TestJenkinsService
import com.trevorism.gcloud.webapi.service.TestWorkCompleteEventProducer
import org.junit.Before
import org.junit.Test

/**
 * @author tbrooks
 */
class JenkinsControllerTest {

    JenkinsController jenkinsController = new JenkinsController()

    @Before
    void setup(){
        jenkinsController.jenkinsService = new TestJenkinsService()
        jenkinsController.eventProducer = new TestWorkCompleteEventProducer()
    }

    @Test
    void testCreateJob() {
        assert jenkinsController.createJob(null, new CreateJenkinsJob(name: "unitTest"))
    }

    @Test
    void testListAllJobs() {
        assert jenkinsController.listAllJobs()
    }

    @Test
    void testGetJob() {
        assert jenkinsController.getJob("unitTest")
    }

    @Test
    void testDeleteJob() {
        assert jenkinsController.deleteJob(null, "unitTest")
    }

    @Test
    void testInvokeJob() {
        assert jenkinsController.invokeJob(null, "unitTest")
    }
}
