Feature: Validations of inputs DTO's

  Scenario: Failed to create user, because status isn't informed
    Given login "tester"
    And password "123"
    And account type "normal"
    And status null
    When create an user
    Then must return error, because status is null
    And a response status equals to 400

  Scenario: Failed to create user, because account type isn't informed
    Given login "tester"
    And password "123"
    And account type null
    And status "ative"
    When create an user
    Then must return error, because account type is null
    And a response status equals to 400

  Scenario: Failed to create user, because password isn't informed
    Given login "tester"
    And password null
    And account type "normal"
    And status "ative"
    When create an user
    Then must return error, because password is null
    And a response status equals to 400

  Scenario: Failed to create user, because password isn't informed
    Given login null
    And password "123"
    And account type "normal"
    And status "ative"
    When create an user
    Then must return error, because login is null
    And a response status equals to 400