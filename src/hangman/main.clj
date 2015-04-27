(ns hangman.main
  (:require [clojure.core.async :refer [chan alt!! timeout thread]]))

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

(defn- show-input-dialog! [sentence]
  (javax.swing.JOptionPane/showInputDialog sentence))

(defn- show-message-dialog! [sentence]
  (javax.swing.JOptionPane/showMessageDialog nil sentence))

(defn- write-word [word hits]
  (apply str (map #(if(contains? hits %) % "_ ") (seq word))))

(defn- ask-for-character-swing! []
  (show-input-dialog! "Informe um caractere."))

(defn- show-win-message-swing! []
  (show-message-dialog! "Você GANHOU!"))

(defn- show-lose-message-swing! []
  (show-message-dialog! "Você PERDEU!"))

(defn new-game [word] 
  {:word word :chs (into #{} (seq word)) :hits #{} :mistakes 4 :message nil})

(defn win? [game]
  (= (count (:hits game)) (count (:chs game))))

(defn lose? [game]
  (zero? (:mistakes game)))

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

(defn start-hangman! [word]
  (loop [game (new-game word)]
    (cond 
      (lose? game) (show-lose-message-swing!)
      (win? game) (show-win-message-swing!)
      :else (let [c (ask-for-character-swing!)
                  game' (with-attempt game c)]
              (show-message-dialog! (message game'))
              (recur game')))))


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

(defn start-hangman-server! [requests responses]
  (thread
    (<!!? requests)
    (loop []
      (let [request (<!!? requests)]
        (>!!? responses 
              (cond 
                (= request :lose?) false
                (= request :win?) true
                :else head)))
      (recur)
      )))






