# CraigslistCrawler

### Problem: collect all renting information on this page (Page 1 only):
  https://sfbay.craigslist.org/d/apts-housing-for-rent/search/apa
  
  including: title/rent price/detail url/hood/

* Example:
  
  Full studio with private entrance, include all utilities & laundry,
  
  $1400,
 
  https://sfbay.craigslist.org/sby/apa/d/full-studio-with-private/6577288734.html,
  
  (san jose south)
  
### Input/Output data
* Input: given URL ("https://sfbay.craigslist.org/d/apts-housing-for-rent/search/apa")
* Output: display the complete result on console, also stored parts of results in log file named "log_out.txt".

### Code:
* In a single class "CraigslistCrawler.java"

### Dependencies:
* json-20160807.jar
* jsoup-1.10.1.jar
* junit-4.12.jar
* lucene-analyzers-common-4.0.0.jar
* lucene-core-4.0.0.jar
* lucene-queryparser-4.0.0.jar
* lucene-suggest-4.0.0.jar
* mysql-connector-java-5.1.40-bin.jar
* servlet-api-3.1.jar
* spymemcached-2.12.1.jar
