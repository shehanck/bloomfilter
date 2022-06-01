# bloomfilter

                          Bloom-Filter based Spell Checker Application

  What is it?
  -----------

  Application that uses an own implementation of Bloom Filter data structure and Spell Checker library utilize that 
  Bloom Filter data structure to facilitate uses with custom lookups. 

  Documentation
  -------------

  Available in Bloom_Filter_Based_Spell_Checker_Doc_Shehan.pdf


  System Requirements
  -------------------

  JDK:
    1.8 or above 
  Memory:
    No minimum requirement. Bloom filter will take 256KB and determining false positivity will require some more space to load the file.
  Disk:
    No minimum requirement. Jar file will be just under 1MB. Therefore few MBs would facilitate the work.
  Operating System:
    No minimum requirement. Can run on both Windows and Unix based environments.

  Running the Application
  ----------------
  
  1) Clone the source code from github repository.
  
  2) Navigate to the workspace and perform a Maven build to generate the JAR file.
		mvn clean install
		
  3) It will generate the JAR file bloom-filter-1.0-SNAPSHOT.jar in target folder.
	 If you have the JAR file downloaded in other way, you can directly proceed with below steps
	 
  4) Go to the JAR file location and run the JAR file using below command
		java -jar bloom-filter-1.0-SNAPSHOT.jar
		
		Command line arguments can be passed to jar file using below options.
			-bitmapsize - Desired Bitmap size
			-hashcount - Desired no: of hash values
			-file - Dictionary file path
			
			If any of above option is not provided, below default values will be used.
				-bitmapsize - 2100000
				-hashcount - 4
				-file - /words.txt
				
			Ex:
				java -jar bloom-filter-1.0-SNAPSHOT.jar
				java -jar bloom-filter-1.0-SNAPSHOT.jar -bitmapsize 10000 
				java -jar bloom-filter-1.0-SNAPSHOT.jar -hashcount 5
				java -jar bloom-filter-1.0-SNAPSHOT.jar -bitmapsize 50000 -hashcount 3 -file /testwords.txt
				
  5) When application starts, it will initialize the Bloom Filter with respective bitmapsize and hashcount values and load the dictionary from respective path.
  
  6) Then it will provide user with 3 options to do lookups.
  
		1 - Lookup a Custom Word
		2 - Lookup a Random Word (5-Character)
		3 - Lookup 10 Random Words (5-Character)
		
  7) Option 1 will request user to enter the item and do the lookup, 
	 Option 2 will generate a random 5 character string and do the lookup, 
	 Option 3 will generate 10 strings of 5 characters and do the lookup
	 
  8) For each lookup, application will display user with lookup result as 'Found' or 'Not Found' 
     along with a small summary including total lookup count, total success count and total false positive count.

  9) Enter 'exit' to quit the application anytime.
