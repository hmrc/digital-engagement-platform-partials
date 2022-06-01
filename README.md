
# digital-engagement-platform-partials

This service provides container content element, required DataPass attributes and Nuance framework script or can 
pull in a HMRC chatskin.

There are three versions of the container elements that can be used. Versions one and two pull in Nuance chat skins,
and version three pulls in the HMRC chatskin. 

The versions can have two types of skin called Embedded and Popup and each skin can be used for a Webchat or a
Digital Assistant.

There is also Reactive and Proactive. This is set up in the Nuance portal but is something you may need to be aware of.

### Reactive chatskin
Is where a user is expected to complete an action to trigger a webchat or Digital Assistand. For example click on 
a button or a link.

### Proactive chatskin
Is where no action is required for a user to take action to trigger a webchat or Virtual Assistant 

### Popup chatskin
A chatskin that is generally used in other MDTP services. A Popup skin sits in the bottom right of a page and does not 
obscure any content on the page the service uses.

### Embedded chatskin
A chatskin that takes up the whole paged and is only used in digital-engagement-platform-frontend. This service serves links from gov.uk 
and do not require content on the page.

### Version 1
Known as DAv1, Webchat version 1 or Popup chatskin. Provides a Nuance popup chatskin used for both Webchat and Digital Assistant.

### Version 2
Known as DAv2, CUI or Embedded chatskin. Provides a full page Embedded chatskin.

### Version 3
Known as DAv3, CIAPI or HMRC chatskin. This was developed mainley to covercone Accessability issues with the Nuance chatcking 
provided in versions one and two. As is stands a fully developed Embedded chatskin has been developed. A Popup chat still has
further work to make this skin usable. 

### Run the application
The application runs on port 9109

To run all the DEP services executed the following command 
**sm --start DIGITAL_ENGAGEMENT_PLATFORM_ALL -r**

Stop the service and use **sbt run** command to rul locally

### Testing
To run the unit test use **sbt test**

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
