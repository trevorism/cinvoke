package com.trevorism.gcloud

import com.trevorism.gcloud.webapi.model.CreateJenkinsJob
import com.trevorism.gcloud.webapi.model.JenkinsJob
import com.trevorism.http.headers.HeadersHttpClient
import com.trevorism.http.headers.HeadersJsonHttpClient
import com.trevorism.http.util.ResponseUtils
import com.trevorism.secure.PasswordProvider
import gherkin.deps.com.google.gson.Gson
import gherkin.deps.com.google.gson.GsonBuilder
import gherkin.deps.com.google.gson.reflect.TypeToken

/**
 * @author tbrooks
 */
class JenkinsTestClient {

    HeadersHttpClient client = new HeadersJsonHttpClient()
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()
    PasswordProvider passwordProvider = new PasswordProvider()

    boolean delete(String jobName){
        String responseJson = ResponseUtils.getEntity client.delete("http://cinvoke.datastore.trevorism.com/job/$jobName", ["Authorization":passwordProvider.password])
        return Boolean.valueOf(responseJson)
    }

    boolean create(CreateJenkinsJob jenkinsJob){
        String json = gson.toJson(jenkinsJob)
        String responseJson = ResponseUtils.getEntity client.post("http://cinvoke.datastore.trevorism.com/job", json, ["Authorization":passwordProvider.password])
        return Boolean.valueOf(responseJson)
    }

    boolean invoke(String jobName){
        String responseJson = ResponseUtils.getEntity client.post("http://cinvoke.datastore.trevorism.com/job/$jobName/build", "{}", ["Authorization":passwordProvider.password])
        return Boolean.valueOf(responseJson)
    }

    List<JenkinsJob> list(){
        String responseJson = ResponseUtils.getEntity client.get("http://cinvoke.datastore.trevorism.com/job", ["Authorization":passwordProvider.password])
        return gson.fromJson(responseJson, new TypeToken<List<JenkinsJob>>(){}.getType())
    }

    JenkinsJob get(String jobName){
        String responseJson = ResponseUtils.getEntity client.get("http://cinvoke.datastore.trevorism.com/job/$jobName", ["Authorization":passwordProvider.password])
        return gson.fromJson(responseJson, JenkinsJob)
    }

}
