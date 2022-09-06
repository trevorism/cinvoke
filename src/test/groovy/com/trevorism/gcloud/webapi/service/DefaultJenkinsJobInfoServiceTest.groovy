package com.trevorism.gcloud.webapi.service

import com.trevorism.http.HttpClient
import org.junit.Test

import javax.ws.rs.NotFoundException

class DefaultJenkinsJobInfoServiceTest {

    @Test(expected = NotFoundException)
    void testGetLastCompletedBuild() {
        DefaultJenkinsJobInfoService jobInfoService = new DefaultJenkinsJobInfoService()
        jobInfoService.client = [get: {s -> "<>"}] as HttpClient
        jobInfoService.getLastCompletedBuild("test")
    }
    
}
