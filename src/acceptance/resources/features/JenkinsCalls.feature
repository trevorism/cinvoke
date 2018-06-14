Feature: Jenkins Calls return expected values
  We should be able to create, read, delete, and invoke jobs.

  Scenario: Create a new jenkins job
    Given the application is alive
    And the jenkins job "zzzTESTzzz" does not exist
    When a jenkins job named "zzzTESTzzz" is created
    Then the create request returns true, indicating success
    And the jenkins job "zzzTESTzzz" can be retrieved

  Scenario: Delete a jenkins job
    Given the application is alive
    And a jenkins job named "zzzTESTzzz" is created up front
    When a jenkins job named "zzzTESTzzz" is deleted
    Then the delete request returns true, indicating success
    And the jenkins job "zzzTESTzzz" cannot be retrieved

  Scenario: List all jenkins jobs
    Given the application is alive
    When the jobs list is requested
    Then a list of jobs is returned

  Scenario: Invoke a job
    Given the application is alive
    And a jenkins job named "zzzTESTzzz" is created
    When the jenkins job "zzzTESTzzz" is invoked
    Then the invoke request returns true, indicating success

  Scenario: Create a jenkins job that already exists
    Given the application is alive
    And a jenkins job named "zzzTESTzzz" is created
    When a jenkins job named "zzzTESTzzz" is created
    Then the create request returns false, indicating failure
    And the jenkins job "zzzTESTzzz" can be retrieved

  Scenario: Delete a jenkins job that does not exist
    Given the application is alive
    And the jenkins job "zzzTESTzzz" does not exist
    When a jenkins job named "zzzTESTzzz" is deleted
    Then the delete request returns false, indicating failure
    And the jenkins job "zzzTESTzzz" cannot be retrieved
