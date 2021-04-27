@testtag
Feature: eLearning Registration and Validation
 

  @smoke
  Scenario Outline: eLearning Registration and Validation
    Given User should be navigated to elearning site "http://elearningm1.upskills.in/"
    When User clicks on Sign up link
    And User enter below registration details
    		|FirstName|Chaitanya          |
    		|LastName | B                 |
    		|EMail    |Chaitanya.b@abc.com|
    		|UserName |Chaitanya          |
    		|Pass     |Chaitanya123       | 
    Then User validates Registration details
   			|FirstName|Chaitanya          |
    		|LastName | B                 |
    		|EMail    |Chaitanya.b@abc.com|
    And User sends an email "<toMailId>" "<subject>" "<message>" 
    And User validates success message "<toMailId>"
    Then User logouts from the application
    
    Examples:
    |toMailId           |subject     |message     |
    |virat kohli (virat)|Test Subject|Test Message|