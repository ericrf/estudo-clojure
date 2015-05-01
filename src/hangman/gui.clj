(ns hangman.gui
  (:require [hangman.main :refer :all]
            [hangman.tools :refer :all]
            [clojure.core.async :refer [chan]]))

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

(defn -main []
  (let [requests (chan)
        responses (chan)]
    (start-hangman-server! requests responses)
    (>!!? requests (show-input-dialog! "Informe a palavra secreta."))
    (loop []
      (let [lose (do
                  (>!!? requests :lose?)
                  (<!!? responses))
            win (do
                  (>!!? requests :win?)
                  (<!!? responses))]
        (cond
         lose (show-lose-message-swing!)
         win (show-win-message-swing!)
         :else (let [c (ask-for-character-swing!)]
                 (>!!? requests c)
                 (show-message-dialog! (<!!? responses))
                 (recur)))))))
