# Vmh
Vmh - Variable Mob Hieght


Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.Rikurob:Vmh:${vmh_version}'
	}
	
	
Step 3. In your root gradle.properties add vmh=version.

A list of versions can be found here: https://github.com/Rikurob/Vmh/releases.
