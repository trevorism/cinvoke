package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.CreateJenkinsJob
import com.trevorism.gcloud.webapi.model.JenkinsJob

/**
 * @author tbrooks
 */
interface JenkinsService {

    List<JenkinsJob> list()
    JenkinsJob get(String jobName)
    boolean delete(String jobName)
    boolean create(CreateJenkinsJob job)
    boolean invoke(String jobName)
}