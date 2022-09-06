package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.JobInfo

interface JenkinsJobInfoService {

    JobInfo getLastCompletedBuild(String jobName)
    JobInfo getLastSuccessfulBuild(String jobName)
    JobInfo getLastFailedBuild(String jobName)


}