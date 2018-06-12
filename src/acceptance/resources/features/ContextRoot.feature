Feature: Context Root of this API
  In order to use the API, it must be available

  Scenario: HTTP GET on the ContextRoot
    Given the application is alive
    When I navigate to "https://cinvoke-dot-trevorism-gcloud.appspot.com/"
    Then then a link to the help page is displayed

  Scenario: HTTP GET on the ContextRoot DNS
    Given the application is alive
    When I navigate to "http://cinvoke.datastore.trevorism.com"
    Then then a link to the help page is displayed
