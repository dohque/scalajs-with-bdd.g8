
Feature: Hello $name;format="Camel"$

  As Alice
  I want to see Hello$name;format="Camel"$
  So that I know the framework works

  Scenario: Simple Hello $name;format="Camel"$

    Given I have a web browser
    When  I open the App
    Then  I see "Hello $name;format="Camel"$!"
