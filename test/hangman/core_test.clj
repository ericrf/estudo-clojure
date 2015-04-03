(ns hangman.core-test
  (:require [midje.sweet :refer :all]
            [hangman.main :refer :all]))

(fact "new game is not lost" 
      (let [game (new-game "word")]
        (lose? game) => false))  

(fact "new game is not won" 
      (let [game (new-game "word")]
        (win? game) => false))

(fact "attempt is right" 
      (let [game (new-game "word")]
        (with-attempt game "w") => {:word "word" :chs (into #{} (seq "word")) :hits #{\w } :mistakes 4 :message "w_ _ _ "}))

(fact "attempt is wrong" 
      (let [game (new-game "word")]
        (with-attempt game "s") => {:word "word" :chs (into #{} (seq "word")) :hits #{} :mistakes 3 :message head}))


;(do (require 'midje.repl) (midje.repl/autotest))