package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.CreateJenkinsJob
import com.trevorism.gcloud.webapi.model.JenkinsJob

/**
 * @author tbrooks
 */
class TestJenkinsService implements JenkinsService{
    @Override
    List<JenkinsJob> list() {
        [new JenkinsJob(name: "unitTest")]
    }

    @Override
    JenkinsJob get(String jobName) {
        return new JenkinsJob(name: jobName)
    }

    @Override
    boolean update(String jobName, List<String> tasks) {
        return jobName
    }

    @Override
    boolean delete(String jobName) {
        return jobName
    }

    @Override
    boolean create(CreateJenkinsJob job) {
        return job?.name
    }

    @Override
    boolean invoke(String jobName) {
        return jobName
    }
}
