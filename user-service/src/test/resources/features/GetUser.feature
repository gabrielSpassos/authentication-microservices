Feature: Get user

  Scenario: Get user by id with success
    Given id "5bf05abde9ecf0748b3cc64b"
    When search the users by id
    Then must return the user id "5bf05abde9ecf0748b3cc64b"
    And a response status equals to 200

  Scenario: Error trying to get user with nonexistent id
    Given id "999"
    When search the users by id
    Then must return error informing the user not exists
    And a response status equals to 400

  Scenario: Get user by login and password with success
    Given login "tester"
    And password "123"
    When search the users by login and password
    Then must return the user id "5bf05abde9ecf0748b3cc64b"
    And a response status equals to 200

  Scenario: Error trying to get user with nonexistent combination of login and password
    Given login "tester"
    And password "notCorrectPass"
    When search the users by login and password
    Then must return error informing the user not exists
    And a response status equals to 400

  Scenario: Error trying to get user by login not informing password
    Given login "tester"
    And none password
    When search the users by login and password
    Then must return error informing the need of a password
    And a response status equals to 400