(ns vim-fireplace-talk.core 
(:import org.sodbeans.phonemic.TextToSpeechFactory
  (:gen-class)))


(def sp (org.sodbeans.phonemic.TextToSpeechFactory/getDefaultTextToSpeech) )

(defn set-options [& {:keys [speed pitch voice volume]}]; :or {speed nil pitch nil voice nil volume nil}}]
  (when speed (.setSpeed sp speed)) ;hmm you kind of want to know if it succeeded
  (when pitch (.setPitch sp pitch))
  ;(when voice (.setVoice sp voice))
  (when volume (.setVolume sp volume)))

(defn get-options [] (zipmap [:speed :pitch :voice :volume] [ (.getSpeed sp) (.getPitch sp) (.getAvailableVoices sp) (.getVolume sp)]))
(say "food fighters love their music too but slow" {:speed 1.0})

(defn apply-kw
    "Like apply, but f takes keyword arguments and the last argument is
      not a seq but a map with the arguments for f"
    [f & args]
    {:pre [(map? (last args))]}
    (apply f (apply concat (butlast args) (last args))))
(defn say 
  ([text] (.speak sp text))
  ([text opts] 
   (let [prev-opts (get-options)]
    ;something something blocking non-blocking
    (apply-kw set-options opts)
    (say text)
    (apply-kw set-options prev-opts))))

(def speech-methods '(.destroyNative .finalize .getAvailableVoices .getTextToSpeechEngine .getVoicesNative .initializeNative .isSpeaking .isSpeakingNative .pause .pauseNative .reinitialize .reinitializeNative .respeak .resume .resumeNative .setPitch .setPitchNative .setSpeechDispatcherPitch .setSpeed .setSpeedNative .setVoice .setVoiceNative .setVolume .setVolumeNative .speak .speakBlocking .speakBlockingNative .speakCharBlockingNative .speakCharNative .speakNative .stop .stopNative))
;(all-methods (first (iterator-seq (.getAvailableVoices sp))))

(defn get-voices  []
   (->> (.getAvailableVoices sp) 
       iterator-seq                              ;language is just an enum with only english in it lol.
       (map #(assoc :name (.getName %) :language (-> % .getLanguage .toString )))))

(defn voices [sp] (sort (map #(.getName %) (iterator-seq (.getAvailableVoices sp)))))

(defn set-voice "arg is map {:name \"donald\"} " [v] 
  (let [voices (iterator-seq (.getAvailableVoices sp))
        voice-obj (first (filter #(= (:name v) (.getName %)) voices ))]
    (.setVoice sp voice-obj)))

;(.setSpeed  sp  (- 0.1 (.getSpeed sp)))
;;getAvailableVoices is a java iterator needs special conversion
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

(def change-speed #(.setSpeed  sp  (% 0.1 (.getSpeed sp))))
(def change-volume #(.setVolume  sp  (% 0.1 (.getVolume sp)))) 
(def lower-speed (partial change-speed -))
(def inc-speed (partial change-speed +)) 
(def lower-volume (partial change-volume -))
(def inc-volume (partial change-volume +))
;;i.e.
;;(str-invoke sp "getSpeed")
;;
;;(get-it sp :speed) 
;;(set-it sp :speed)

;;perhaps a wrapper around these setters which
;; 1. lets you know if that setting is not possible
;; 2. let's you revert to previous state


;; create speech with custom settings key-words--instantiate and set options 
;; set-options method takes arbitrary keyword args <>
;; convert voice to map 
;;
;; speech methods take arbitrary settings, set them, speak, reset (does this work?) <>
;;
;; optionally eval the interior of what's in say  with an eval flag
;; (say :volume 2 :eval true (http/get freebase.org/ ....
;;
;;

;(def say #(.speak sp %)) 
;(def state { :speed 0.5 ....
;(def prev-state state)
