
# digital-engagement-platform-partials

This service provides container content element, required DataPass attributes and Nuance framework script or can 
pull in a HMRC chat skin.

There are three versions of the container elements that can be used. Versions one and two pull in Nuance chat skins,
and version three pulls in the HMRC chat skin. The method getPartials is used inspect the correct ids and pull in the correct view.

Each version can be one of two types of skin: Embedded or Popup and each skin can be used for a Webchat or a
Digital Assistant.

There is also Reactive and Proactive. This is set up in the Nuance portal but is something you may need to be aware of.

### Reactive chat skin
Is where a user is expected to complete an action to trigger a webchat or Digital Assistand. For example click on 
a button or a link.

### Proactive chat skin
Is where no action is required for a user to take action to trigger a webchat or Virtual Assistant. 

### Popup chat skin
A Popup skin sits in the bottom right of a page and does not obscure any content on the page the service uses.

### Embedded chat skin
A chat skin that takes up the whole paged and is used when there is no content on the page.

### Version 1
Popup chat skin, provides a Nuance popup chat skin used for both Webchat and Digital Assistant.

### Version 2
Embedded chat skin provides a full page Embedded chat skin.

### Version 3
HMRC chat skin. As is stands a fully developed Embedded chat skin has been developed. A Popup chat still has
further work to make this skin usable. 

### Run the application
The application runs on port 9109

To run all the DEP services executed the following command 
**sm --start DIGITAL_ENGAGEMENT_PLATFORM_ALL -r**

To run digital-engagement-platform-partials locally:
run **sm --stop digital-engagement-platform-partials**
run **sbt run**

### Testing
To run the unit test use **sbt test**

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
