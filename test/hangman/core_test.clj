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
  
  (fact "correct letters win the game"
        (-> game 
            (with-attempt "a")
            (with-attempt "l")
            (with-attempt "i")
            (with-attempt "g")
            (with-attempt "t")
            (with-attempt "o")
            (with-attempt "r")
            win?) => true)
  
  (let [game (new-game "aligator")]
   (let [game' (-> game (with-attempt "w"))
         game'' (-> game' (with-attempt "s"))
         game'''(-> game'' (with-attempt "p"))
         game''''(-> game''' (with-attempt "x"))]
     (fact "first error" (:message game') => head)
     (fact "second error" (:message game'') => body)
     (fact "third error" (:message game''') => arms)
     (fact "last error" (:message game'''') => legs)
     (fact "lose the game " (lose? game'''') => true))))

;(do (require 'midje.repl) (midje.repl/autotest))