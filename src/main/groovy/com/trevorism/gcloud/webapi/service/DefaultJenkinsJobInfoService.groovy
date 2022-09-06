package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.JobInfo
import com.trevorism.http.HttpClient
import com.trevorism.http.JsonHttpClient
import groovy.json.JsonSlurper

import javax.ws.rs.NotFoundException

class DefaultJenkinsJobInfoService implements JenkinsJobInfoService {

    private HttpClient client = new JsonHttpClient()

    @Override
    JobInfo getLastCompletedBuild(String jobName) {
        JsonSlurper slurper = new JsonSlurper()
        def number = getBuildNumber(slurper, jobName, "lastCompletedBuild")
        return createJobInfo(jobName, number, slurper)
    }

    @Override
    JobInfo getLastSuccessfulBuild(String jobName) {
        JsonSlurper slurper = new JsonSlurper()
        def number = getBuildNumber(slurper, jobName, "lastSuccessfulBuild")
        return createJobInfo(jobName, number, slurper)
    }

    @Override
    JobInfo getLastFailedBuild(String jobName) {
        JsonSlurper slurper = new JsonSlurper()
        def number = getBuildNumber(slurper, jobName, "lastFailedBuild")
        if (number == -1) {
            return new JobInfo([id: "-1"])
        }
        return createJobInfo(jobName, number, slurper)
    }

    private int getBuildNumber(JsonSlurper slurper, String jobName, String type) {
        String jobJson = client.get("${JenkinsService.JENKINS_URL}/job/${jobName}/api/json")
        if (jobJson.startsWith("<")) {
            throw new NotFoundException("Jenkins job with name ${jobName} not found.")
        }

        def job = slurper.parseText(jobJson)
        def number = job."${type}"?.number
        return number ?: -1
    }

    private JobInfo createJobInfo(String jobName, number, JsonSlurper slurper) {
        String buildJson = client.get("${JenkinsService.JENKINS_URL}/job/${jobName}/${number}/api/json")
        def build = slurper.parseText(buildJson)
        return new JobInfo([
                id               : build.id,
                building         : build.building,
                duration         : build.duration,
                estimatedDuration: build.estimatedDuration,
                result           : build.result,
                date             : new Date(build.timestamp),
                url              : build.url
        ])
    }
}
