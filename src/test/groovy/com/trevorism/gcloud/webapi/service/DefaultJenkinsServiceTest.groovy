package com.trevorism.gcloud.webapi.service

import com.trevorism.gcloud.webapi.model.CreateJenkinsJob
import com.trevorism.gcloud.webapi.model.JenkinsJob
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.http.headers.HeadersHttpClientBase
import org.apache.http.HttpEntity
import org.apache.http.ProtocolVersion
import org.apache.http.StatusLine
import org.apache.http.client.methods.CloseableHttpResponse
import org.junit.Before
import org.junit.Test

/**
 * @author tbrooks
 */
class DefaultJenkinsServiceTest {

    DefaultJenkinsService service

    @Before
    void setup(){
        service = new DefaultJenkinsService()

    }

    @Test
    void testList() {
        service.client = [get:{url, headers -> createCloseableHttpResponse("{}")}] as HeadersHttpClient
        assert !service.list()
    }

    @Test
    void testGet() {
        service.client = [get:{url, headers -> createCloseableHttpResponse("{}")}] as HeadersHttpClient
        JenkinsJob job = service.get("unitTest")
        assert job.name == "unitTest"
    }

    @Test
    void testDelete() {
        service.client = [get:{url, headers -> createCloseableHttpResponse("{}")}, post: {url, json, headers -> createCloseableHttpResponse("{}")}] as HeadersHttpClient
        assert service.delete("unitTest")

    }

    @Test
    void testCreate() {
        service.client = [get:{url, headers -> createCloseableHttpResponse("{}")}] as HeadersHttpClient
        service.xmlClient = [post: {url, json, headers -> createCloseableHttpResponse("{}")}] as HeadersHttpClientBase
        assert service.create(new CreateJenkinsJob(name: "unitTest"))
    }

    @Test
    void testUpdate() {
        service.client = [get:{url, headers -> createCloseableHttpResponse("{}")}] as HeadersHttpClient
        service.xmlClient = [post: {url, json, headers -> createCloseableHttpResponse("{}")}] as HeadersHttpClientBase
        assert service.update("unitTest", ["clean", "build"])
    }

    @Test
    void testInvoke() {
        service.client = [get:{url, headers -> createCloseableHttpResponse("{}")}, post: {url, json, headers -> createCloseableHttpResponse("{}", 201)}] as HeadersHttpClient
        assert service.invoke("unitTest")
    }

    static CloseableHttpResponse createCloseableHttpResponse(String responseString, int statusCode = 200){
        Closure getContentClosure = { -> new ByteArrayInputStream( responseString.getBytes() ) }
        Closure getContentLengthClosure = {->Long.valueOf(responseString.size())}
        Closure getStatusLineClosure = {-> return [getStatusCode: {-> return statusCode}] as StatusLine}
        HttpEntity entity = [getContentLength: getContentLengthClosure, getContentType: {return null}, getContent: getContentClosure, isStreaming:{->true}] as HttpEntity
        CloseableHttpResponse response = [getEntity:{ -> entity}, getStatusLine: getStatusLineClosure] as CloseableHttpResponse
        return response
    }
}
