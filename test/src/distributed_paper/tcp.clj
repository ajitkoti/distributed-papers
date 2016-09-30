(ns distributed-paper.tcp
  (:require
    [manifold.deferred :as d]
    [manifold.stream :as s]
    [clojure.edn :as edn]
    [aleph.tcp :as tcp]
    [gloss.core :as gloss]
    [gloss.io :as io]))



(defn ping-ack-msg [type id membership-list]
  {:msg type
   :id id
   :members membership-list})

(defn send-ping [stream id membership-list] (s/put! stream (ping-ack-msg :ping id membership-list)))

(def protocol
  (gloss/compile-frame
    (gloss/finite-frame :uint32
                        (gloss/string :utf-8))
    pr-str
    edn/read-string))

(defn wrap-duplex-stream
  [protocol s]
  (let [out (s/stream)]
    (s/connect
      (s/map #(io/encode protocol %) out)
      s)

    (s/splice
      out
      (io/decode-stream s protocol))))


(defn client
  [host port]
  (d/chain (tcp/client {:host host, :port port})
           #(wrap-duplex-stream protocol %)))

(defn start-server
  [handler port]
  (tcp/start-server
    (fn [s info]
      (handler (wrap-duplex-stream protocol s) info))
    {:port port}))

(defn fast-echo-handler
  [f]
  (fn [s info]
    (s/connect
      (s/map f s)
      s)))

(defn gossip-message-handler
  [s _]
  (let [msg (s/take! s)]
    (println msg)))

#_(def gossip-server
    (start-server
      gossip-message-handler
      10000))

#_(def s
   (start-server
     (fast-echo-handler inc)
     10000))

#_(def c @(client "localhost" 10000))

