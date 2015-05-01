(ns hangman.async2-test
  (:require [midje.sweet :refer :all]
            [hangman.main :refer :all]
            [clojure.core.async :refer [chan alt!! timeout thread sliding-buffer]]))


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


(defn- prepare []
  (let [requests (chan)
        responses (chan)]
    (start-hangman-server! requests responses)
    (>!!? requests "aligator")
    [requests responses]))

(defn- get-response [request-list]
  (let [[requests responses] (prepare)]
    (reduce (fn [_ request]
              (>!!? requests request)
              (<!!? responses))
            nil
            request-list)))

; PAREDIT

(tabular "Hangman"
  (fact "Responds correctly"
    (get-response ?requests) => ?response)
         ?response ?requests                            ?fact      ;FITNESSE - FIT (Ward Cunningham)
         false     [:lose?]                             "New game is not lost"
         false     [:win?]                              "New game is not won"
         head      ["w"]                                "first mistake"
         true      ["a" "l" "i" "g" "t" "o" "r" :win?]  "Win"
         true      ["x" "z" "v" "b" :lose?]             "Lose")



;(do (require 'midje.repl) (midje.repl/autotest))