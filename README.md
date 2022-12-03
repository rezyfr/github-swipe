Main Feature
-------------
* Show github user search result with SwipeableCard 
* Load the next page of search result if the SwipeableCard reach its threshold

Tech Stack
----------
* Jetpack Compose
* Coroutine + Flow
* Retrofit
* MVVM
* Landscapist Coil
* Hilt for DI
* Modularization

Environment
-----------
Build with:
- Target SDK = 33
- Build Tools 30.0.3
- AGP 7.4

How-to
------
![ezgif com-gif-maker](https://user-images.githubusercontent.com/22698234/205417213-3be03c79-6591-4894-a8e0-27aa2249ee42.gif)

Known bug
------
If the last result search is swiped until the last item, the first index of next search result is not showing because the swipeDirection is filled (not null)
