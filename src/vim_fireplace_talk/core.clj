(ns vim-fireplace-talk.core 
(:import org.sodbeans.phonemic.TextToSpeechFactory
  (:gen-class)))

(def sp (org.sodbeans.phonemic.TextToSpeechFactory/getDefaultTextToSpeech) )
(def say #(.speak sp %)) 

(def speech-methods '(.destroyNative .finalize .getAvailableVoices .getTextToSpeechEngine .getVoicesNative .initializeNative .isSpeaking .isSpeakingNative .pause .pauseNative .reinitialize .reinitializeNative .respeak .resume .resumeNative .setPitch .setPitchNative .setSpeechDispatcherPitch .setSpeed .setSpeedNative .setVoice .setVoiceNative .setVolume .setVolumeNative .speak .speakBlocking .speakBlockingNative .speakCharBlockingNative .speakCharNative .speakNative .stop .stopNative))
;(all-methods (first (iterator-seq (.getAvailableVoices sp))))

(defn voices [sp] (sort (map #(.getName %) (iterator-seq (.getAvailableVoices sp)))))

(defn set-voice "arg is string" [sp v] 
  (let [voices (iterator-seq (.getAvailableVoices sp))
        voice-obj (first (filter #(= v (.getName %)) voices ))]
    (.setVoice sp voice-obj)))
   (set-voice sp "default") 
;(.setSpeed  sp  (- 0.1 (.getSpeed sp)))
;;getAvailableVoices is a java iterator needs special conversion
;;
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
(.speakBlocking sp "Hello World")
(say "hello there chap")
  (println "Hello, World!"))


 (defn str-invoke [instance method-str & args]
               (clojure.lang.Reflector/invokeInstanceMethod 
                                 instance 
                                 method-str 
                                 (to-array args)))

;;i.e.
;;(str-invoke sp "getSpeed")
