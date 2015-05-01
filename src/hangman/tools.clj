(ns hangman.tools
  (:require [clojure.core.async :refer [alt!! timeout <! >!]]))
  
(defn <!!?
  ([ch]
    (<!!? ch 200))
  ([ch timeout-millis]
    (alt!!
      (timeout timeout-millis) :timeout
      ch ([v] v))))

(defn >!!?
  ([ch v]
    (>!!? ch v 200))
  ([ch v timeout-millis]
    (alt!!
      (timeout timeout-millis) false
      [[ch v]] true)))

