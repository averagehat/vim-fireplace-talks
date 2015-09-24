(defproject vim-fireplace-talk "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main ^:skip-aot vim-fireplace-talk.core
  :target-path "target/%s"

  ;;!!!!!!!
  ;;custom and important settings for sucessful build.
  ;;lein localrepo [list|install|etc.]
  :plugins [[lein-localrepo "0.5.3"]] 
  :repositories {"local" ~(str (.toURI (java.io.File. "maven_repository")))}
  ;this is equivalent to running java with this option.
  ;needed to staisfy link for JNI.
  :jvm-opts ["-Djava.library.path=jni"]
  :dependencies [[org.clojure/clojure "1.7.0"] 
                 ;;note the jar name, jar was added with:
                 ;;  mvn deploy:deploy-file -Dfile=resources/phonemic.jar -DartifactId=phonemic -Dversion=2 -DgroupId=phonemic -Dpackaging=jar -Durl=file:maven_repository 
                 [phonemic/phonemic "2"] ] 
  :profiles {:uberjar {:aot :all}})
