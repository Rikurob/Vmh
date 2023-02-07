# Vmh
Vmh - Variable Mob Hieght

Note:The released versions on CurseForge are currently not the same here as I have been having trouble setting up the repositories but I am working on it.

Step 1. Add it in your root build.gradle at the end of repositories:

	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
	
	
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.Rikurob:Vmh:${vmh_version}'
	}
	
	
	
Step 3. In your root gradle.properties add vmh_version=....

A list of versions can be found here: https://github.com/Rikurob/Vmh/releases.

[![](https://jitpack.io/v/Rikurob/Vmh.svg)](https://jitpack.io/#Rikurob/Vmh)

