package com.trevorism.gcloud.webapi.controller

import com.trevorism.event.WorkCompleteEventProducer
import com.trevorism.event.model.WorkComplete
import com.trevorism.gcloud.webapi.model.CreateJenkinsJob
import com.trevorism.gcloud.webapi.model.JenkinsJob
import com.trevorism.gcloud.webapi.service.DefaultJenkinsService
import com.trevorism.gcloud.webapi.service.JenkinsService
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.secure.Roles
import com.trevorism.secure.Secure
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType

/**
 * @author tbrooks
 */
@Api("Jenkins Operations")
@Path("/job")
class JenkinsController {

    private JenkinsService jenkinsService = new DefaultJenkinsService()
    private WorkCompleteEventProducer eventProducer = new WorkCompleteEventProducer()

    @ApiOperation(value = "Create a new jenkins job **Secure")
    @POST
    @Secure(Roles.SYSTEM)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    boolean createJob(@Context HttpHeaders headers, CreateJenkinsJob job){
        boolean result = jenkinsService.create(job)
        String correlationId = headers?.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY)
        eventProducer.sendEvent(new WorkComplete("trevorism-gcloud", "cinvoke", correlationId))
        return result
    }

    @ApiOperation(value = "Updates a jenkins job **Secure")
    @PUT
    @Secure(Roles.SYSTEM)
    @Path("{job}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    boolean updateJob(@Context HttpHeaders headers, @PathParam("job") String jobName, List<String> tasks){
        boolean result = jenkinsService.update(jobName, tasks)
        String correlationId = headers?.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY)
        eventProducer.sendEvent(new WorkComplete("trevorism-gcloud", "cinvoke", correlationId))
        return result
    }


    @ApiOperation(value = "Get a list of all jenkins jobs")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<JenkinsJob> listAllJobs(){
        jenkinsService.list()
    }

    @ApiOperation(value = "Get a specific job **Secure")
    @GET
    @Path("{job}")
    @Secure(Roles.USER)
    @Produces(MediaType.APPLICATION_JSON)
    JenkinsJob getJob(@PathParam("job") String jobName){
        new JenkinsJob(name: jenkinsService.get(jobName))
    }

    @ApiOperation(value = "Delete a job **Secure")
    @DELETE
    @Secure(Roles.SYSTEM)
    @Path("{job}")
    @Consumes(MediaType.APPLICATION_JSON)
    boolean deleteJob(@Context HttpHeaders headers, @PathParam("job") String jobName){
        boolean result = jenkinsService.delete(jobName)
        String correlationId = headers?.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY)
        eventProducer.sendEvent(new WorkComplete("trevorism-gcloud", "cinvoke", correlationId))
        return result
    }

    @ApiOperation(value = "Invoke a job **Secure")
    @POST
    @Secure(Roles.SYSTEM)
    @Path("{job}/build")
    @Produces(MediaType.APPLICATION_JSON)
    boolean invokeJob(@Context HttpHeaders headers, @PathParam("job") String jobName){
        boolean result = jenkinsService.invoke(jobName)
        String correlationId = headers?.getHeaderString(HeadersHttpClient.CORRELATION_ID_HEADER_KEY)
        eventProducer.sendEvent(new WorkComplete("trevorism-gcloud", "cinvoke", correlationId))
        return result
    }

}
