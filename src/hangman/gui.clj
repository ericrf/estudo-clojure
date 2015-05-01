(ns hangman.gui
  (:require [hangman.main :refer :all]))

(defn- show-input-dialog! [sentence]
  (javax.swing.JOptionPane/showInputDialog sentence))

(defn- show-message-dialog! [sentence]
  (javax.swing.JOptionPane/showMessageDialog nil sentence))

(defn- ask-for-character-swing! []
  (show-input-dialog! "Informe um caractere."))

(defn- show-win-message-swing! []
  (show-message-dialog! "Você GANHOU!"))

(defn- show-lose-message-swing! []
  (show-message-dialog! "Você PERDEU!"))

(defn start-hangman! [word]
  (loop [game (new-game word)]
    (cond
      (lose? game) (show-lose-message-swing!)
      (win? game) (show-win-message-swing!)
      :else (let [c (ask-for-character-swing!)
                  game' (with-attempt game c)]
              (show-message-dialog! (message game'))
              (recur game')))))

(defn -main
  []
  (start-hangman! "altz"))
