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
  
  (fact "wrong letters lose the game "
        (-> game 
            (with-attempt "w")
            (with-attempt "p")
            (with-attempt "s")
            (with-attempt "k")
            lose?) => true
        1 => 1)
  
  (fact "first error"
        (-> game
            (with-attempt "w")
            message) => head)
  
  (fact "second error"
      (-> game
          (with-attempt "w")
          (with-attempt "s")
          message) => body)
  
  (fact "third error"
      (-> game
          (with-attempt "w")
          (with-attempt "s")
          (with-attempt "x")
          message) => arms)
  
  (fact "last error"
      (-> game
          (with-attempt "w")
          (with-attempt "s")
          (with-attempt "x")
          (with-attempt "z")
          message) => legs)        
)

;(do (require 'midje.repl) (midje.repl/autotest))