package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.CreateJenkinsJob
import com.trevorism.gcloud.webapi.model.JenkinsJob
import org.junit.Test

/**
 * @author tbrooks
 */
class JenkinsServiceTest {
    //@Test
    void testList() {
        DefaultJenkinsService djs = new DefaultJenkinsService()
        println djs.list()
    }

    //@Test
    void testGet() {
        DefaultJenkinsService djs = new DefaultJenkinsService()
        println djs.get("registry")
        println djs.get("dias")
    }

    //@Test
    void testDelete() {
        DefaultJenkinsService djs = new DefaultJenkinsService()
        djs.delete("trev1")
    }

    //@Test
    void testCreate() {
        DefaultJenkinsService djs = new DefaultJenkinsService()
        djs.create(new CreateJenkinsJob(name: "trev1", gradleTasks: ["clean"]))
    }

    //@Test
    void testInvoke() {
        DefaultJenkinsService djs = new DefaultJenkinsService()
        //djs.invoke(new JenkinsJob(jobName: "create-project"))
    }
}
