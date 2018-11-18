@Validation
Feature: Validations of inputs DTO's

  Scenario: Failed to create character, because doesn't inform char name
    Given user id "5beeddf9271c06542585634d"
    And character name null
    And character class "Mage"
    When create the user character
    Then must return error, because char name is null
    And a response status equals to 400

  Scenario: Failed to create character, because doesn't inform char class
    Given user id "5beeddf9271c06542585634d"
    And character name "TestChar"
    And character class null
    When create the user character
    Then must return error, because char class is null
    And a response status equals to 400

  Scenario: Failed to update character, because doesn't inform char class name
    Given user id "5beeddf9271c06542585634d"
    And character name "TestChar"
    And character class null
    When update the user character
    Then must return error, because char class is null
    And a response status equals to 400