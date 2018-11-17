Feature: Create user

  Scenario: Create an user with success
    Given login "tester"
    And password "123"
    And account type "normal"
    And status "ative"
    When create an user
    Then must create an user
    And a response status equals to 200