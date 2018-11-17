Feature: Update user

  Scenario: Update an user with sucess
    Given id "5bf076e2e9ecf010850e9e39"
    And login "update"
    And password "abc"
    And account type "premium"
    And status "ative"
    When update an user
    Then must update the user
    And a response status equals to 200
