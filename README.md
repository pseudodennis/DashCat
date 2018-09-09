# DashCat

## Inspiration
We were thinking, with all the cat videos that make it onto YouTube, how many great missed cat moments are there?

## What it does
This app allows you to run your webcam all the time, and it will save clips of a specified object, such as "cat." It uses AppleScript to save a QuickTime video to a mov file, then uses javacv to extract keyframes from the video, it sends the frames to Clarifi to see if they match the specified keyword, then if so, it saves the file to a safe place.

## Challenges I ran into
Getting frames from a camera into Java for processing. Ultimately we used some AppleScript to access the built-in MacBook camera.

## Accomplishments that I'm proud of
We figured out how to utilize the javacv library to extract keyframes and pipe them into Clarifi.

## What I learned
Sometimes the most amount of creativity and focus happens with the least amount of sleep!

## What's next for DashCat
Improve the performance and add the ability to upload to a YouTube playlist automatically.
