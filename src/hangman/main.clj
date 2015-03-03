(ns hangman.main)

(def head  
  "      _______
     |/      |
     |      (_)
     |       
     |       
     |      
     |
    _|___")

(def body  
  "      _______
     |/      |
     |      (_)
     |       |
     |       |
     |      
     |
    _|___")

(def arms  
  "      _______
     |/      |
     |      (_)
     |      |||
     |       |
     |      
     |
    _|___")

(def legs  
  "      _______
     |/      |
     |      (_)
     |      |||
     |       |
     |      | |
     |
    _|___")



(def hits (java.util.HashSet.))
(def chs (java.util.HashSet.))
;(def word (java.lang.String. (javax.swing.JOptionPane/showInputDialog "Informe a palavra chave")))

(defn err-handler [mistakes]
  (case mistakes
    4 (javax.swing.JOptionPane/showMessageDialog nil head)
    3 (javax.swing.JOptionPane/showMessageDialog nil body)
    2 (javax.swing.JOptionPane/showMessageDialog nil arms)
    1 (javax.swing.JOptionPane/showMessageDialog nil legs)))

(defn end-game []
  (javax.swing.JOptionPane/showMessageDialog nil "Você GANHOU!")
  (System/exit 0))

(defn success-handler [c]
  (.add hits c)
  (javax.swing.JOptionPane/showMessageDialog nil "Você acertou!")
  (if (= (.size hits) (.size chs)) (end-game)))

;(defn write-riddle []
;  (loop [i 0]
;    (when(< i (.length word))
;      (println (java.lang.Boolean/valueOf (.contains hits (.charAt word i))))
;      (recur (+ i 1)))))

;(loop [i (.length word)]
;    (when (> i 0)
;      (.add chs (.charAt word (- i 1)))
;      (recur (- i 1))))

(defn start-game []
  (loop [mistakes 4]
    (when (> mistakes 0)
      (def c (javax.swing.JOptionPane/showInputDialog "Informe um caractere"))
      (if (.contains word c) (success-handler c) (err-handler mistakes))
      (if-not (.contains word c) (def r (- mistakes 1)) (def r mistakes))
      (recur r)))
  (javax.swing.JOptionPane/showMessageDialog nil "Você perdeu"))
