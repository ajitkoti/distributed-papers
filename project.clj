(defproject distributed-paper "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.391"]
                 [aleph "0.4.2-alpha1"]
                 [gloss "0.2.5"]
                 [compojure "1.3.3"]]


  :main ^:skip-aot distributed-paper.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
