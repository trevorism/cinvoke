package com.trevorism.gcloud.webapi.model

import io.swagger.annotations.ApiModelProperty

/**
 * @author tbrooks
 */
class CreateJenkinsJob {

    @ApiModelProperty(value = "The jenkins job name", dataType = "string")
    String name

    @ApiModelProperty(value = "The git repository name", dataType = "string")
    String gitRepoName

    @ApiModelProperty(value = "The gradle tasks to run", example = '["clean","build","publishSnapshot"]')
    List<String> gradleTasks

    @ApiModelProperty(value = "Create a job with no build trigger", dataType = "boolean")
    boolean noBuildTrigger
}
