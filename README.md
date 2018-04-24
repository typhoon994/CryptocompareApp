# Cryptocompare App

### How to run project:
Clone following url https://github.com/typhoon994/CryptocompareApp.git via git.
When done open it as existing project in android studio. Do the clean build and you are ready to go.

#### Implementation notes:
1. For data caching have used Room library from Android SDK, turned out that its light-weight SQLite wrapper.
2. For brevity used manually created Thread instances. Preffered way with larger scale project is Reactive approach
with RxKotlin and RxAndroid. Used schedulers for this purposes.
3. Instead of using setter for passing DB param to ViewModel, recommended way for larger scale projects to use
dependency injection approach with Dagger2 library.





