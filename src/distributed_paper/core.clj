(ns distributed-paper.core
  (:gen-class)
  (require '[clojure.core.async :as async :refer [<!! >!! timeout chan alt!!]]))




(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn server [port])

(defn ping [channel membership])

