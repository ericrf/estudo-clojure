(ns hangman.core-test
  (:require [midje.sweet :refer :all]
            [hangman.main :refer :all]))

(let [game (new-game "aligator")]
  (defn- with-attempts [game, guesses]
    (reduce with-attempt game guesses))

  (fact "new game is not lost" 
        (lose? game) => false)
  
  (fact "new game is not won" 
        (win? game) => false)
  
  (fact "correct letters win the game" 
        (win? (with-attempts game ["a" "l" "i" "g" "t" "o" "r"])) => true)
 
  (fact "attempt is right" 
        (message (with-attempts game ["a" "l" "r"])) => "al_ _ a_ _ r")
  
  (fact "first error"
        (message (with-attempts game ["w"])) => head)
 
  (fact "second error"
        (message (with-attempts game ["w" "s"])) => body)
 
  (fact "third error"
        (message (with-attempts game ["w" "s" "p"])) => arms)
 
  (fact "last error"
        (message (with-attempts game ["w" "s" "p" "x"])) => legs)
 
  (fact "lose the game"
        (lose? (with-attempts game ["w" "s" "p" "x"])) => true)
 
)

;(do (require 'midje.repl) (midje.repl/autotest))