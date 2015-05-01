(ns hangman.main
  (:require [clojure.core.async :refer [chan go <! >!]]))

(def head  
  "      _______
     |/      |
     |      (_)
     |       
     |       
     |      
     |
    _|___")

(def body  
  "      _______
     |/      |
     |      (_)
     |       |
     |       |
     |      
     |
    _|___")

(def arms  
  "      _______
     |/      |
     |      (_)
     |      |||
     |       |
     |      
     |
    _|___")

(def legs  
  "      _______
     |/      |
     |      (_)
     |      |||
     |       |
     |      | |
     |
    _|___")

(defn new-game [word] 
  {:word     word
   :chs      (into #{} (seq word))
   :hits     #{}
   :mistakes 4
   :message  nil})

(defn win? [game]
  (= (count (:hits game)) (count (:chs game))))

(defn lose? [game]
  (zero? (:mistakes game)))

(defn- write-word [word hits]
  (apply str (map #(if(contains? hits %) % "_ ") (seq word))))

(defn with-attempt [game c]
  (if (.contains (:word game) c)
    (let [game' (update-in game [:hits] into c)]
      (assoc game'
           :message (write-word (:word game') (:hits game'))))
    (let [game' (update-in game [:mistakes] dec)]
      (assoc game'
           :message (get [legs arms body head] (:mistakes game'))))))

(defn message [game]
  (:message game))

(defn start-hangman-server! [requests responses]
  (go
    (loop [game (new-game (<! requests))]
      (when-let [request (<! requests)]
        (let [[game' response] (cond
                                 (= request :lose?) [game (lose? game)]
                                 (= request :win?) [game (win? game)]
                                 :else (let [game' (with-attempt game request)]
                                         [game' (message game')]))]
          (>! responses response)
          (recur game'))))))



