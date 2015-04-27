(ns hangman.async2-test
  (:refer-clojure :exclude [reduce])
  (:require [midje.sweet :refer :all]
            [hangman.main :refer :all]
            [clojure.core.async :refer [chan alt!! timeout thread reduce]]))

(defn- with-attempts [game, guesses]
    (reduce with-attempt game guesses))

(fact "new game is not lost" 
      (let [requests (chan)
            responses (chan)]
        (start-hangman-server! requests responses)
        (>!!? requests "aligator")
        (>!!? requests :lose?)
        (<!!? responses) => false))

(fact "new game is not won" 
        "new game is not lost" 
      (let [requests (chan)
            responses (chan)]
        (start-hangman-server! requests responses)
        (>!!? requests "aligator")
        (>!!? requests :win?)
        (<!!? responses) => false))
 
(fact "first error"
      (let [requests (chan)
            responses (chan)]
        (start-hangman-server! requests responses)
        (>!!? requests "aligator")
        (>!!? requests "w")
        (<!!? responses) => head))


(fact "correct letters win the game"
      (let [requests (chan)
            responses (chan)]
        (start-hangman-server! requests responses)
        (>!!? requests "aligator")
        (>!!? requests "a")
        (println (<!!? responses))
        (>!!? requests "l")
        (println (<!!? responses))
        (>!!? requests "i")
        (println (<!!? responses))
;        (reduce >!!? requests ["a" "l" "i" "g" "t" "o" "r"])
        (>!!? requests :win?)
        (<!!? responses) => true))

;(do (require 'midje.repl) (midje.repl/autotest))