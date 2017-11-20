# Pics <img src="/.github/assets/pics-icon.png" height="36"/>
[![Build Status](https://travis-ci.org/jobinlawrance/pics.svg?branch=master)](https://travis-ci.org/jobinlawrance/pics)
[![codecov](https://codecov.io/gh/jobinlawrance/pics/branch/master/graph/badge.svg)](https://codecov.io/gh/jobinlawrance/pics)  

Android app that loads and displays images from unsplash.com

This is one of my hobby projects where I test out new libraries, architectural and design patterns etc. This project is in its infancy right now and nothing much happens in terms of functionality yet.

### Project Description
Pics uses Model-View-Intent architecture with the help of mosbi-mvi library by Hannes Dormann. Check [this series](http://hannesdorfmann.com/android/mosby3-mvi-1) for more on that.


### Cloning the Project
Create a values resource file keys.xml at app/src/main/res/values/ and add your unsplash application id
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="unsplash_id">xxxxxxxxxxxxxxx</string>
</resources>
```
### Features
#### Complete
1. Home Screen with Pagination
1. Image Downloading

#### Ongoing
1. Details Screen UI
1. Launcher widget

#### Upcoming
1. Home screen pagination.
1. Unsplash user login
1. Download Screen
1. Staggerred grid view
and much more..

### Screenshots

| Homescreen | |
|:-:|:-:|
| ![First](/.github/assets/pics-home.png?raw=true) | ![First](/.github/assets/pics-home-land.png?raw=true)  |

### Credits
UI of the app is heavily influenced by [Plaid app](https://github.com/nickbutcher/plaid) by [Nick Butcher](https://twitter.com/crafty)
