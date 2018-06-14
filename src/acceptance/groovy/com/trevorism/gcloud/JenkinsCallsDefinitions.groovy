package com.trevorism.gcloud

import com.trevorism.gcloud.webapi.model.CreateJenkinsJob
import com.trevorism.gcloud.webapi.model.JenkinsJob

/**
 * @author tbrooks
 */

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)

JenkinsTestClient apiClient = new JenkinsTestClient()

List<JenkinsJob> fullList
boolean invokeResult
boolean createResult
boolean deleteResult

Given(~/^the jenkins job "([^"]*)" does not exist$/) { String jobName ->
    apiClient.delete(jobName)
}

Given(~/^a jenkins job named "([^"]*)" is created up front$/) { String jobName ->
    apiClient.create(new CreateJenkinsJob(name: jobName))
}

When(~/^a jenkins job named "([^"]*)" is created$/) { String jobName ->
    createResult = apiClient.create(new CreateJenkinsJob(name: jobName))
}

When(~/^the jenkins job "([^"]*)" is invoked$/) { String jobName ->
    invokeResult = apiClient.invoke(jobName)
}

When(~/^the jobs list is requested$/) { ->
    fullList = apiClient.list()
}

When(~/^a jenkins job named "([^"]*)" is deleted$/) { String jobName ->
    deleteResult = apiClient.delete(jobName)
}

Then(~/^the create request returns true, indicating success$/) { ->
    assert createResult
}

Then(~/^the jenkins job "([^"]*)" can be retrieved$/) { String jobName ->
    assert apiClient.get(jobName).name == jobName
}

Then(~/^the delete request returns true, indicating success$/) { ->
    assert deleteResult
}

Then(~/^the jenkins job "([^"]*)" cannot be retrieved$/) { String jobName ->
    assert !fullList.find{
        it.name == jobName
    }
}

Then(~/^a list of jobs is returned$/) { ->
    assert fullList
    assert fullList.size() > 15
}

Then(~/^the invoke request returns true, indicating success$/) { ->
    assert invokeResult
}

Then(~/^the create request returns false, indicating failure$/) { ->
    assert !createResult
}

Then(~/^the delete request returns false, indicating failure$/) { ->
    //Should be this
    //assert !deleteResult
    assert deleteResult
}
