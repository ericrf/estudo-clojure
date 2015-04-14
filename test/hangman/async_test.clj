(ns hangman.async_test
  (:require [midje.sweet :refer :all]
            [clojure.core.async :as async :refer :all]))



; Consome tudo que for colocado no canal
;(let [c (chan)]
;  (thread
;    (loop []
;      (println "altz: " (<!! c))
;      (recur)))
;  (dotimes [n 5]
;    (>!! c n)))


; Consome tudo que foi colocado no canal e fica esperando mais...
;(let [c (chan (sliding-buffer 2))]
;  (thread
;    (loop [i 4]
;      (Thread/sleep 1000)
;      (cond
;        (> i 0) (println "altz: " i ":" (<!! c)))
;        :else
;          (recur (dec i))))
;  (dotimes [n 5]
;    (>!! c n))
;  (println "fim!"))



