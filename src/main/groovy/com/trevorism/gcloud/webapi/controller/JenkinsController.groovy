package com.trevorism.gcloud.webapi.controller

import com.trevorism.gcloud.webapi.model.CreateJenkinsJob
import com.trevorism.gcloud.webapi.model.JenkinsJob
import com.trevorism.gcloud.webapi.service.DefaultJenkinsService
import com.trevorism.gcloud.webapi.service.JenkinsService
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * @author tbrooks
 */
@Api("Jenkins Operations")
@Path("/jobs")
class JenkinsController {

    private JenkinsService jenkinsService = new DefaultJenkinsService()

    @ApiOperation(value = "Create a new jenkins job **Secure")
    @POST
    @Secure
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    boolean createJob(CreateJenkinsJob job){
        jenkinsService.create(job)
    }

    @ApiOperation(value = "Get a list of all jenkins jobs")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<JenkinsJob> listAllJobs(){
        jenkinsService.list()
    }

    @ApiOperation(value = "Get a specific job")
    @GET
    @Path("{job}")
    @Produces(MediaType.APPLICATION_JSON)
    JenkinsJob getJob(@PathParam("job") String jobName){
        new JenkinsJob(name: jenkinsService.get(jobName))
    }

    @ApiOperation(value = "Delete a job **Secure")
    @DELETE
    @Secure
    @Consumes(MediaType.APPLICATION_JSON)
    boolean deleteJob(JenkinsJob job){
        jenkinsService.delete(job.name)
    }

    @ApiOperation(value = "Invoke a job **Secure")
    @POST
    @Secure
    @Path("{job}/build")
    @Produces(MediaType.APPLICATION_JSON)
    boolean invokeJob(@PathParam("job") String jobName){
        jenkinsService.invoke(jobName)
    }

}
