(ns hangman.core-test
  (:require [midje.sweet :refer :all]
            [hangman.main :refer :all]))
(let [game (new-game "aligator")]
  (fact "new game is not lost" 
        (lose? game) => false)
  
  (fact "new game is not won" 
        (win? game) => false)
  
  (fact "attempt is right" 
        (message (with-attempt game "a")) => "a_ _ _ a_ _ _ ")
  
  (fact "attempt is wrong"
        (message (with-attempt game "s")) => head)  
)

;(do (require 'midje.repl) (midje.repl/autotest))