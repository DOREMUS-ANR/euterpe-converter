euterpeConverter
================

Convert foreseen performances from Philharmonie base Euterpe


## How to run:

1. Setup the `config.properties` file as you need.
2. Run `gradle run` in the project folder.


## Known problems

- ##### SAXParseException: Content is not allowed in prolog
    Remove the BOM - see [stackoverflow](https://stackoverflow.com/a/13176742/1218213)