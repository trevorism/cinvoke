package com.trevorism.gcloud.webapi.model

import io.swagger.annotations.ApiModelProperty

/**
 * @author tbrooks
 */
class JenkinsJob {

    static JenkinsJob NULL_OBJECT = new JenkinsJob()

    @ApiModelProperty(value = "The jenkins job name", dataType = "string")
    String name

    @Override
    String toString() {
        return name
    }
}
