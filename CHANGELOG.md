#CHANGELOG- Sunderland Coding Assessment

# Version 1.21.6.4 Release Notes
  - TODO - Improvement - Updated the presentation of remote data in recycler view to use custom linear layout manager with carousel+zoom effect


# Version 1.21.6.3 Release Notes
  - TODO - Improvement - Added local alternate images related to prior local primary images, modified the local data presentation to include alternate, selectable images 


# Version 1.21.6.2 Release Notes
  - Improvement - Separated presentation of local data from remote data for each of the Sneakers, Athletes, and Apparel screens, local data presented in ViewPager2 while remote data presented in RecyclerView


# Version 1.21.6.1 Release Notes
  - Testing - Some minor changes to the splash screen instrumented test   


# Version 1.21.6.0 Release Notes
  - Testing - Broke splash screen delay logic out into a more easily testable function and added unit and instrumentation test cases
  - Improvement - Updated the splash screen transition delay to use the type-safe Duration class as opposed to Long delay
  - Bug Fix - Splash screen gets stuck if the screen is turned off while the coroutine is running
  - General - Updated dependencies


# Version 1.21.5.0 Release Notes
  - Baseline release