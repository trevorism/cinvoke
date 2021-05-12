package com.trevorism.gcloud

import com.trevorism.gcloud.webapi.model.CreateJenkinsJob
import com.trevorism.gcloud.webapi.model.JenkinsJob
import com.trevorism.https.DefaultSecureHttpClient
import com.trevorism.https.SecureHttpClient
import gherkin.deps.com.google.gson.Gson
import gherkin.deps.com.google.gson.GsonBuilder
import gherkin.deps.com.google.gson.reflect.TypeToken

/**
 * @author tbrooks
 */
class JenkinsTestClient {

    SecureHttpClient client = new DefaultSecureHttpClient()
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()

    boolean delete(String jobName){
        String responseJson = client.delete("https://cinvoke.datastore.trevorism.com/job/$jobName")
        return Boolean.valueOf(responseJson)
    }

    boolean create(CreateJenkinsJob jenkinsJob){
        String json = gson.toJson(jenkinsJob)
        String responseJson = client.post("https://cinvoke.datastore.trevorism.com/job", json)
        return Boolean.valueOf(responseJson)
    }

    boolean invoke(String jobName){
        String responseJson = client.post("https://cinvoke.datastore.trevorism.com/job/$jobName/build", "{}")
        return Boolean.valueOf(responseJson)
    }

    List<JenkinsJob> list(){
        String responseJson = client.get("https://cinvoke.datastore.trevorism.com/job")
        return gson.fromJson(responseJson, new TypeToken<List<JenkinsJob>>(){}.getType())
    }

    JenkinsJob get(String jobName){
        String responseJson = client.get("https://cinvoke.datastore.trevorism.com/job/$jobName")
        return gson.fromJson(responseJson, JenkinsJob)
    }

}
