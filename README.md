# PopularTV App

Sample app to show popular tv shows from the [movie db api](https://developers.themoviedb.org/3/tv/get-popular-tv-shows).<br />
This app is created following a MVVM architectural pattern, using some of the solutions provided by Android (ViewModel & LiveData).
For now (and for the sake of simplicity), all code is based in a single module. As the app could grow, I would create different modules based on features (core - app - feature1- feature2....).<br />

**Some considerations for the actual app:**
* No fragments used (In a modular app, I would follow the approach suggested by Android with [navigation components](https://developer.android.com/guide/navigation)
* No database used
* Dagger 2 is used for DI (in a project with multiple modules I would consider to use [dagger-android](https://google.github.io/dagger/android))

**Next steps:**
* Implement repository pattern. Add a database solution (Room), an create a repository which would manage the data (network -> database-> ui). Then update the viewmodel class (inject repository instead of the network provider)
* Use data binding in the main activity (along with [viewmodel](https://developer.android.com/topic/libraries/data-binding/architecture))
* Add instrumented tests
