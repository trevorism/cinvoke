package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.CreateJenkinsJob
import com.trevorism.gcloud.webapi.model.JenkinsJob

/**
 * @author tbrooks
 */
interface JenkinsService {

    public static final String JENKINS_URL = "https://trevorism-build.eastus.cloudapp.azure.com"

    List<JenkinsJob> list()
    JenkinsJob get(String jobName)
    boolean update(String jobName, List<String> tasks)
    boolean delete(String jobName)
    boolean create(CreateJenkinsJob job)
    boolean invoke(String jobName)
}