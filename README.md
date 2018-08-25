# FlickrGallery
======================

This sample showcases the following Architecture Components:
* [Model]
* [View]
* [Presenter]

Introduction
-------------
### Features

This sample is a Image Gallery solution with a custom Image Loader. It has a search feature to search for pictures based on interest.

#### Model
 * It is an interface responsible for managing data.


#### View Layer
* A main activity that hosts the fragment with image Gallery.
* A Base Fragment to encapsulates all the common features of the Fragments.
* A fragment to display the list of pictures in a grid view.


#### Presentation layer
* Presenter 
Presenter is the middle-man between the View and Model. It encapsulates all the Presentation logic. The presenter is responsible for querying the model and updating the view, reacting to user interactions updating the model.

#### UnitTesting
JUinit Testcase has been written for Presenter. We can directly run in Android Studio by Right Clicking on the Class and select "Run <test class>
. And to see the coverage we have t the select "Run <test class> with Coverage"

#### InstrumentationTestCase
UI Test case has been written for View in Espresso.

#### ImageLoader
A custom image loader makes a network call for downloading image and load it to the imageview. Using DataBinding for a hassale-free solution.

License
--------

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
