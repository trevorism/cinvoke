package com.trevorism.gcloud.webapi.service

import com.google.gson.Gson
import com.trevorism.gcloud.webapi.model.CreateJenkinsJob
import com.trevorism.gcloud.webapi.model.Crumb
import com.trevorism.gcloud.webapi.model.JenkinsJob
import com.trevorism.gcloud.webapi.model.JobsList
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.http.headers.HeadersHttpClientBase
import com.trevorism.http.headers.HeadersJsonHttpClient
import com.trevorism.http.util.ResponseUtils
import groovy.xml.XmlUtil
import org.apache.http.client.methods.CloseableHttpResponse

/**
 * @author tbrooks
 */
class DefaultJenkinsService implements JenkinsService {

    private HeadersHttpClient client = new HeadersJsonHttpClient()
    private HeadersHttpClientBase xmlClient = createTextXmlHttpClient()

    private final Gson gson = new Gson()
    private final Properties properties
    private String jenkinsUrl
    private String username
    private String password


    DefaultJenkinsService() {
        properties = new Properties()
        properties.load(DefaultJenkinsService.class.getClassLoader().getResourceAsStream("secrets.properties") as InputStream)

        jenkinsUrl = properties.getProperty("url")
        username = properties.getProperty("username")
        password = properties.getProperty("password")
    }

    @Override
    List<JenkinsJob> list() {
        CloseableHttpResponse response = client.get("${jenkinsUrl}/api/json?depth=1", [:])
        String jobsJson = ResponseUtils.getEntity response
        JobsList list = gson.fromJson(jobsJson, JobsList)
        return list.jobs
    }

    @Override
    JenkinsJob get(String jobName) {
        CloseableHttpResponse response = client.get("${jenkinsUrl}/job/${jobName}", [:])
        int statusCode = response.statusLine.statusCode
        ResponseUtils.closeSilently response
        if (statusCode != 404)
            return new JenkinsJob(name: jobName)
        return JenkinsJob.NULL_OBJECT
    }

    @Override
    boolean update(String jobName, List<String> tasks) {
        CreateJenkinsJob createJenkinsJob = createUpdateObject(jobName, tasks)
        return xmlPost(createJenkinsJob, "${jenkinsUrl}/job/${jobName}/config.xml")
    }

    private CreateJenkinsJob createUpdateObject(String jobName, List<String> tasks) {
        CreateJenkinsJob createJenkinsJob = new CreateJenkinsJob()
        createJenkinsJob.name = jobName
        createJenkinsJob.gitRepoName = jobName
        createJenkinsJob.gradleTasks = tasks
        createJenkinsJob
    }

    @Override
    boolean delete(String jobName) {
        CloseableHttpResponse response = client.post("${jenkinsUrl}/job/${jobName}/doDelete", "{}", createHeaders(crumb))
        int statusCode = response.statusLine.statusCode
        ResponseUtils.closeSilently response
        return statusCode != 404
    }

    @Override
    boolean create(CreateJenkinsJob job) {
        xmlPost(job, "${jenkinsUrl}/createItem?name=${job.name}")
    }

    private boolean xmlPost(CreateJenkinsJob job, String url){
        job = validateJob(job)
        String updatedXml = updateXmlTemplate(job)
        CloseableHttpResponse response = xmlClient.post(url, updatedXml, createHeaders(crumb))
        int statusCode = response.statusLine.statusCode
        ResponseUtils.closeSilently response
        return statusCode == 200
    }

    private HeadersHttpClientBase createTextXmlHttpClient() {
        HeadersHttpClient client = new HeadersHttpClientBase() {
            @Override
            protected String getMediaType() {
                return "text/xml"
            }
        }
        client
    }

    @Override
    boolean invoke(String jobName) {
        CloseableHttpResponse response = client.post("${jenkinsUrl}/job/${jobName}/build", "{}", createHeaders(crumb))
        int statusCode = response.statusLine.statusCode
        ResponseUtils.closeSilently response
        return statusCode == 201
    }

    private Crumb getCrumb() {
        CloseableHttpResponse response = client.get("${jenkinsUrl}/crumbIssuer/api/json", createHeaders())
        String crumbJson = ResponseUtils.getEntity response
        return gson.fromJson(crumbJson, Crumb)
    }

    private Map createHeaders(Crumb crumb = null) {
        Map headers = [:]
        headers.put("Authorization", getBasicAuthenticationValue())
        if (crumb)
            headers.put(crumb.crumbRequestField, crumb.crumb)
        headers
    }

    private String getBasicAuthenticationValue() {
        String encoded = "$username:$password".bytes.encodeBase64().toString()
        return "Basic ${encoded}"

    }

    private static String updateXmlTemplate(CreateJenkinsJob job) {
        def xml = new XmlSlurper().parse(DefaultJenkinsService.class.getClassLoader().getResourceAsStream("jenkinsConfigTemplate.xml"))
        xml.scm.userRemoteConfigs.'hudson.plugins.git.UserRemoteConfig'.url.replaceBody "https://github.com/trevorism/${job.gitRepoName}.git"
        xml.builders.'hudson.plugins.gradle.Gradle'.tasks.replaceBody job.gradleTasks.join(" ")
        if(job.noBuildTrigger){
            xml.triggers.replaceNode {
                triggers {}
            }
        }
        String updatedXml = XmlUtil.serialize(xml)
        updatedXml
    }

    private static CreateJenkinsJob validateJob(CreateJenkinsJob job) {
        if(!job.name)
            throw new RuntimeException("Unable to create job with no name")
        if (!job.gitRepoName)
            job.gitRepoName = job.name
        if (!job.gradleTasks)
            job.gradleTasks = ["clean", "build"]
        return job
    }
}
