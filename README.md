# Quiz App – DailyRounds Assignment

### Demo Video
https://drive.google.com/file/d/1Gj_nCcQj40zh_xLiUvgPBpu6ICk-ixrS/view?usp=sharing


A modern Android Quiz application built using Kotlin, Jetpack Compose and MVVM Architecture. The app fetches quiz questions from a remote API, allows users to answer or skip questions, tracks quiz statistics such as correct answers and streaks and displays a summary on the result screen.

Features
Splash Screen
Displays the application logo.
Automatically navigates to the Quiz screen after a short delay.
Quiz Screen
Fetches quiz questions from a remote API.
Displays one question at a time.
Randomly shuffles answer options for every question.
Highlights correct and incorrect answers.
Automatically moves to the next question after answer selection.
Allows users to skip questions.
Displays quiz progress using a progress bar.
Shows the current answer streak using a visual indicator.
Smooth animated transition between questions.
Result Screen
Displays:
Total Questions
Correct Answers
Skipped Questions
Longest Streak
Allows users to restart the quiz.
Error Handling
Loading indicator while fetching questions.
Retry option for API failures.
Graceful handling of network errors.
Graceful handling of configuration changes
