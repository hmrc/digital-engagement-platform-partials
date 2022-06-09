
# digital-engagement-platform-partials

## About
This service provides html partials required for using Nuance's Digital Assistant and Webchat. Nuance requires two different kinds of elements per page:

1) A single block that includes the required core Nuance script and data, inserted preferably near the end of the <body> tag. It is critical that this block not be included in the <head> section. The Nuance code will reject any such insertion, and the webchat will not engage.
2) An empty div container element for each chat link required. The Nuance JavaScript code will add content to these container elements to implement the chat as needed. Most HMRC pages have a single fixed chat link, but multiple links are allowed for different usages.

There are also optional HTML partials required for the use of HMRC's customised chat skin. One for an embedded chat skin and one for a popup chat skin. These 2 HTML partials both return a link to the required javascript file and a link to the required CSS file (both hosted on [digital-engagement-platform-skin](https://github.com/hmrc/digital-engagement-platform-skin) service).

The library provides a separate call for each through a single interface (See [digital-engagement-platform-chat](https://github.com/hmrc/digital-engagement-platfrom-chat) readme for further information).

## Running from source
Clone the repository using SSH:

`git@github.com:hmrc/digital-engagement-platform-partials.git`

Run the code from source using

`sbt run`

Dependencies will also need to be started from source or using service manager.

## Running through service manager
The application runs on port 9109

*You need to be on the VPN*

Ensure your service manager config is up to date, and run the following command:

`sm --start DIGITAL_ENGAGEMENT_PLATFORM_ALL -f`

This will start all the required services

## Example of using the service

Start all the required services (see above)

Open your browser and navigate to the following url:

`http://localhost:9956/ask-hmrc/chat/construction-industry-scheme`

View the page source and you'll see required Nuance elements and container.

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
