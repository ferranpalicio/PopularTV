# PopularTV App
"Playground" app to play with some of the last trending stuff from Android devlopment.

**Some considerations given the the actual sate of the app:**
* No fragments used (In a modular app, I would follow the approach suggested by Android with [navigation components](https://developer.android.com/guide/navigation)
* No database used
* Dagger 2 is used for DI (in a project with multiple modules I would consider to use [dagger-android](https://google.github.io/dagger/android))

**Next steps:**
* Add usecase regarding tv shows search
* Use data binding in the main activity (along with [viewmodel](https://developer.android.com/topic/libraries/data-binding/architecture))
* Add instrumented tests
