Feature: Context Root of this API
  In order to use the API, it must be available

  Scenario: ContextRoot on app engine
    Given the application is alive
    When I navigate to "https://cinvoke-dot-trevorism-gcloud.appspot.com"
    Then then a link to the help page is displayed

  Scenario: ContextRoot on dns
    Given the application is alive
    When I navigate to "http://cinvoke.datastore.trevorism.com"
    Then then a link to the help page is displayed

  Scenario: Ping on app engine
    Given the application is alive
    When I ping the application deployed to "https://cinvoke-dot-trevorism-gcloud.appspot.com"
    Then pong is returned, to indicate the service is alive

  Scenario: Ping on dns
    Given the application is alive
    When I ping the application deployed to "http://cinvoke.datastore.trevorism.com"
    Then pong is returned, to indicate the service is alive