Narrative:
In order to query on database for some person
As a person looking for someone
I want to find the person if she is registered
					 
Scenario: Looking for persons
Given looking for a person with number [id]
Then recieves the person fullname [name] who lives in [country]

Examples:
|id|name|country|
|1|Anderson Zanichelli|Brazil|
|2|Bilbo Baggins|The Shire|
|3|Aria Stark|Winterfell|
|4|Walter White|United States|