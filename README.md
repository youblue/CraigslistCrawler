# CraigslistCrawler

### Problem: collect all renting information on this page:
  https://sfbay.craigslist.org/d/apts-housing-for-rent/search/apa
  (Here I assume page means "Page 1", so this page contains 120 records that need collection.)
* The renting information include:
title/rent price/detail url/hood/
* Example:
  Full studio with private entrance, include all utilities & laundry,
  $1400,
  https://sfbay.craigslist.org/sby/apa/d/full-studio-with-private/6577288734.html,
  (san jose south)
o If some fields don’t exist, use NULL instead.

### Input/Output data
* Input: given URL ("https://sfbay.craigslist.org/d/apts-housing-for-rent/search/apa".)
* Output: display the complete result on console, also stored parts of results in log file named "log_out.txt".

### Code:
* In a single class "CraigslistCrawler.java"
* Details of the including methods:
  * 
