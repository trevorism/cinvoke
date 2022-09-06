package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.JenkinsJob
import com.trevorism.gcloud.webapi.model.JobInfo
import com.trevorism.gcloud.webapi.service.DefaultJenkinsJobInfoService
import com.trevorism.gcloud.webapi.service.JenkinsJobInfoService
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Api("Job Info Operations")
@Path("/info")
class JobInfoController {

    private JenkinsJobInfoService service = new DefaultJenkinsJobInfoService()

    @ApiOperation(value = "Get last completed build for the specified job **Secure")
    @GET
    @Path("{job}/completed")
    @Secure(Roles.USER)
    @Produces(MediaType.APPLICATION_JSON)
    JobInfo getLastCompletedBuild(@PathParam("job") String jobName){
        service.getLastCompletedBuild(jobName)
    }

    @ApiOperation(value = "Get last successful build for the specified job **Secure")
    @GET
    @Path("{job}/success")
    @Secure(Roles.USER)
    @Produces(MediaType.APPLICATION_JSON)
    JobInfo getLastSuccessfulBuild(@PathParam("job") String jobName){
        service.getLastSuccessfulBuild(jobName)
    }

    @ApiOperation(value = "Get last failed build for the specified job **Secure")
    @GET
    @Path("{job}/failed")
    @Secure(Roles.USER)
    @Produces(MediaType.APPLICATION_JSON)
    JobInfo getLastFailedBuild(@PathParam("job") String jobName){
        service.getLastFailedBuild(jobName)
    }
}
