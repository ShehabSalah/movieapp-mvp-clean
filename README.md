# movieapp-mvp-clean MVP + Clean Architecture 
### Summary
This sample stands on the principles of [Clean Architecture](https://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html), using MVP for the presentation layer.

It's based on the [MVP sample](https://github.com/ShehabSalah/movieapp-mvp), adding a domain layer between the presentation layer and repositories, splitting the app in three layers:

* **MVP**: Model View Presenter pattern from the base sample.
* **Domain**: Holds all business logic. The domain layer starts with classes named *use cases* or *interactors* used by the application presenters. These *use cases* represent all the possible actions a developer can perform from the presentation layer.
* **Repository**: Repository pattern from the base sample.

Systems based on clean architecture have the following characteristics: separation of concerns, multilayer, independent of frameworks, independent of user interfaces, independent of databases, testable by layer, with a dependency rule that says that code dependencies can only point inwards, from lower levels like the presentation layer, to higher levels like the data layer.

<img src="https://github.com/googlesamples/android-architecture/wiki/images/mvp-clean.png" alt="Diagram"/>

### Key concepts
The big difference with base MVP sample is the use of the Domain layer and *use cases*. Moving the domain layer from the presenters will help to avoid code repetition on presenters.

Use cases define the operations that the app needs. This increases readability since the names of the classes make the purpose obvious.

Use cases are good for operation reuse over our domain code.

The example application is based on the 3-tier of the clean architectural approach: the presentation layer, the domain layer, and the data layer.

![http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture/clean_architecture_layers.png)

The presentation layer is implemented as an MVP design pattern and it represents the application is an Android phone & table module., the domain layer contains the use cases which responsible on communicating with the data layer and applying the business logic, and the data layer is the one who responsible on getting the data from the local database and remote server.

## Clean architecture

To better understand the concept of the 3-tier of the clean architectural with the MVP design pattern, let's see another overview, but from a different perspective.

![http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture/clean_architecture.png)

## Screenshots
<img src="https://user-images.githubusercontent.com/16334887/34884731-51a45c7e-f7c6-11e7-9034-f867bc03bf30.png" width="250"/> <img src="https://user-images.githubusercontent.com/16334887/34884881-c8b36e36-f7c6-11e7-9045-e4a1a4a66c98.png" width="250"/> <img src="https://user-images.githubusercontent.com/16334887/34884920-edd43218-f7c6-11e7-9b2e-7c68566f74ee.png" width="250"/> <img src="https://user-images.githubusercontent.com/16334887/34884954-07e67bde-f7c7-11e7-9e92-8192407a93a8.png" width="250"/> <img src="https://user-images.githubusercontent.com/16334887/34884999-2febb6b2-f7c7-11e7-8949-7987b4181ce0.png" width="250"/> <img src="https://user-images.githubusercontent.com/16334887/34885031-480857c8-f7c7-11e7-85f2-1831977b8de5.png" width="250"/>

## Libraries
This version of the app uses some other libraries:
- Picasso: used for loading, processing, caching and displaying remote and local images.
- ButterKnife: used for perform injection on objects, views and OnClickListeners.
- CardView: used for representing the information in a card manner with a drop shadow and corner radius which looks consistent across the platform.
- RecyclerView: The RecyclerView widget is a more advanced and flexible version of ListView.
- GSON: Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object.
- Retrofit: This library used to send HTTP request to the server and retrieve response.
- ROOM Library: Room provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
- BlurView Library: It blurs its underlying content and draws it as a background for its children.

# The Movie DB API Key is required.
In order for the movieapp-mvp-clean app to function properly as of January 26th, 2018 an API key for themoviedb.org must be included with the build.

Include the unique key for the build by adding the following line to util/Constants.java or find the TODO Line.

<code>
API_KEY = "";
</code>
<br/>
<br/>

## License
```
Copyright (C) 2018 Shehab Salah Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
  
  http://www.apache.org/licenses/LICENSE-2.0
  
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
