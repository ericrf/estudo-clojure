(ns hangman.async2-test
  (:require [midje.sweet :refer :all]
            [hangman.main :refer :all]
            [clojure.core.async :refer [chan alt!! timeout thread sliding-buffer]]))

(defn- with-attempts [game, guesses]
    (reduce with-attempt game guesses))

(defn- prepare []
  (let [requests (chan)
        responses (chan (sliding-buffer 1))]
    (start-hangman-server! requests responses)
    (>!!? requests "aligator")
    [requests responses]))

(defn- assert-response [response & request-list]
  (let [[requests responses] (prepare)]
    (doseq [request request-list] 
      (>!!? requests request))
    (fact "response must match" (<!!? responses) => response)))

(defn- assert-response2 [response request-list]
  (let [[requests responses] (prepare)]
    (doseq [request request-list] 
      (>!!? requests request))
    (fact "response must match" (<!!? responses) => response)))
  
(fact "new game is not lost" 
  (assert-response false :lose?))

(fact "new game is not won"
  (assert-response false :win?))

(fact "first error"
  (assert-response head "w"))

(fact "correct letters win the game"
  (assert-response true "a" "l" "i" "g" "t" "o" "r" :win?))

(fact "lose the game"
  (assert-response true "x" "z" "v" "b" :lose?))

(tabular "soma" 
   (assert-response2 ?a ?b)
    ?a        ?b
    false     [:lose?] 
    false     [:win?]
    
   )

;(do (require 'midje.repl) (midje.repl/autotest))