package com.trevorism.gcloud.webapi.model

import io.swagger.annotations.ApiModelProperty

/**
 * @author tbrooks
 */
class JenkinsJob {

    @ApiModelProperty(value = "The jenkins job name", dataType = "string")
    String jobName
    @ApiModelProperty(value = "The git repository name, usually mateches the job name", dataType = "string")
    String repoName
    @ApiModelProperty(value = "The gradle tasks to run")
    List<String> gradleTasks = []
}
