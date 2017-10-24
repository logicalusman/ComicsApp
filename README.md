# ComicsApp - An illustration of implementing MVVM in an Android app
The app fetches comics from the [Marvel](https://developer.marvel.com) api and shows them in the list. The list represents each
comic by displaying its title and image. You can see further details of a comic once you click on it. Moreover, you can filter
out the comic list by input your buying budget, the app will then show the list of comics within your budget range. You can also
see the sum of pages of the comics in your list.

## Some commentary on the app's components and architecture 
Architecturely, the app implements MVVM pattern to bind its UI to other components. It makes use of RxJava along with 
[AndroidViewModel](https://developer.android.com/reference/android/arch/lifecycle/AndroidViewModel.html) to implement MVVM - 
the ViewModel support library was announced as part of the 
[architecture components](https://developer.android.com/topic/libraries/architecture/index.html) at Google IO 2017.
The network interface uses Retrofit2 and RxJava.

Note that in order to use the archiecture components sdk, you should use Android Studio 3.0.

## Other third pary libs used by the app
Glide - Used to load images to the view as the user scrolls through.

Retrolambda - Used for writing Java8 lambda expressions. 

## Areas of improvement
- The app is tested by setting screen orientation to prortrait mode. Its pending test to work on landscape mode.
- It doesn't cater for tablet (large) screen sizes.
- Lack unit (instrumentation) tests for its ViewModel and network classes.

## How to build and run?
Make sure you have 3.0 version of Anroid Studio in order to build and run this project. Once the project sets up in Android Studio, 
you can launch a simulator via AVD wizard or directly run it on the device connected to your computer. To build the project from command line 
run the following command inside your project root

./gradlew

Once it finishes, get the builds from /app/build/generated/

## Run Unit Test
You can run the unit test via Android Studio or via running the following command inside your project root

./gradlew test

Once it finishes, see the test results at /app/build/test-results
