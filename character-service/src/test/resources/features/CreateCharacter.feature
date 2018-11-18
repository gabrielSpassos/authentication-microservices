@CreateChar
Feature: Create Character

  Scenario: Create character with success
    Given user id "5beeddf9271c06542585634d"
    And character name "TestChar"
    And character class "Mage"
    And this user is a user with normal permissions
    And find an user with the id "5beeddf9271c06542585634d" with status 200
    When create the user character
    Then must return the new character
    And a response status equals to 200

  Scenario: Fail to create another char, no permission
    Given user id "5beeddf9271c06542585634d"
    And character name "TestNewChar"
    And character class "Warrior"
    And this user is a user with normal permissions and already have one char
    And find an user with the id "5beeddf9271c06542585634d" with status 200
    When create the user character
    Then must return the error, because this user hasn't permission to create one more character
    And a response status equals to 400

  Scenario: Fail to create char, user blocked
    Given user id "5beeddf9271c06542585634d"
    And character name "TestChar"
    And character class "Mage"
    And this user is a user blocked user
    And find an user with the id "5beeddf9271c06542585634d" with status 200
    When create the user character
    Then must return error, because the user is blocker
    And a response status equals to 400

  Scenario: Fail to create char, but doesn't find user by id
    Given user id "999"
    And character name "TestChar"
    And character class "Mage"
    And this user is a user not found user
    And find an user with the id "999" with status 400
    When create the user character
    Then must return error, because the user isn't found
    And a response status equals to 400

  Scenario: Create another character with success
    Given user id "hgghjasasduy1231"
    And character name "TestChar2"
    And character class "Warrior"
    And this user is a user with premium permissions
    And find an user with the id "hgghjasasduy1231" with status 200
    When create the user character
    Then must return the new character
    And a response status equals to 200

  Scenario: Fail to create third char, no permission
    Given user id "hgghjasasduy1231"
    And character name "TestChar2"
    And character class "Warrior"
    And this user is a user with premium permissions and two chars
    And find an user with the id "hgghjasasduy1231" with status 200
    When create the user character
    Then must return the error, because this user hasn't permission to create one more character
    And a response status equals to 400