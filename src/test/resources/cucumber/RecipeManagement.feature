Feature: Recipe Management
  Create recipes and list all of them

  Scenario Outline: Recipe creation
    Given the recipe application is up and running
    When a recipe <id> is created
    Then the recipe <id> is added to the database

    Examples:
      | id    |
      | Pasta |
      | Pizza |

  Scenario Outline: Recipe deletion
    Given the recipe application is up and running
    When a recipe <id> is created
    And a recipe <id> is deleted
    Then the recipe <id> is not in the database

    Examples:
      | id    |
      | Pasta |
      | Pizza |