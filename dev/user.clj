;use  :source-paths ["dev"])  in defproject and this will be available in the repl!
;(use 'your.namespace :reload)
(use 'clojure.reflect)
(defn all-methods [x] (->> x reflect :members 
                           (filter :return-type) 
                           (map :name) 
                           sort 
                           (map #(str "." %) )
                           distinct 
                           println))

