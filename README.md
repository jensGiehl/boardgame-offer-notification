# BGG Offer Finder

This is a simple web crawler for the following sites:

* [Spiele-Offensive](https://www.spiele-offensive.de/)
* [Milan Spiele](https://www.milan-spiele.de/)
* [Brettspiel-Angebote](https://www.brettspiel-angebote.de/)

In a scheduler it crawls the page to look for new offers.

To identify a new offer, it persists all data in a database.

After a new offer was identified, it checks the current best price
and gather some information about the game from [BoardgameGeek](https://boardgamegeek.com/).

If it sounds like a great deal it notifies a telegram chat group.

So if you want to run this application you need at least provide the following information:

* `telegram.bot-id`
* `telegram.default-chat-id`