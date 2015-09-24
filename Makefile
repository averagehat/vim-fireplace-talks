#LD_LIBRARY_PATH=$PWD/jni lein run
#any of this stuff still necessary: https://code.google.com/p/vimoir/wiki/InstallPhonemicOnDebian  ?
phonemic:   resources/phonemic.jar
SPEECH_DISPATCHER_VERSION := $(shell speech-dispatcher --version 2>/dev/null)
#linux 
speech-dispatcher:
ifdef SPEECH_DISPATCHER_VERSION
	@echo "Found speech-dispatcher version $(SPEECH_DISPATCHER_VERSION)"
else	
	@echo speech-dispatcher not found, installing
	sudo apt-get install speech-dispatcher
endif


install: lein
	lein uberjar

jni: phonemic
	cp phonemic/phonemic/jni .

resources/phonemic.jar:
	 #can't use prof stefika's repo below because the linux stuff isn't compiled
	 #git clone https://bitbucket.org/stefika/phonemic.git
	 git clonehttps://github.com/hcarver/phonemic
	 cd phonemic/phonemic
	 #this build was actually tested with the pre-built jar file in samples within the bitbucket or sourceforege
	 ant jar #or use the pre-built jar
	 mkdir -p resources/
	 cp phonemic.jar ../../resources/
	 mvn deploy:deploy-file -Dfile=resources/phonemic.jar -DartifactId=phonemic -Dversion=2 -DgroupId=phonemic -Dpackaging=jar -Durl=file:maven_repository


