(ns hangman.core-test
  (:require [midje.sweet :refer :all]
            [hangman.main :refer :all]))

(fact "new game is not lost" 
      (let [game (new-game "word")]
        (lose? game) => false))  

(fact "new game is not won" 
      (let [game (new-game "word")]
        (win? game) => false))

(fact "new game is not won" 
      (let [game (new-game "word")]
        (win? game) => false))

;(do (require 'midje.repl) (midje.repl/autotest))