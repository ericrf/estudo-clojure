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

;TODO:
(def hits (java.util.HashSet.))

(defn err-handler [mistakes]
  (case mistakes
    4 (javax.swing.JOptionPane/showMessageDialog nil head)
    3 (javax.swing.JOptionPane/showMessageDialog nil body)
    2 (javax.swing.JOptionPane/showMessageDialog nil arms)
    1 (javax.swing.JOptionPane/showMessageDialog nil legs)))

(defn end-game []
  (javax.swing.JOptionPane/showMessageDialog nil "Você GANHOU!")
  (System/exit 0))

(defn success-handler [c chs]
  (.add hits c)
  (javax.swing.JOptionPane/showMessageDialog nil "Você acertou!")
  (if (= (.size hits) (count chs)) (end-game)))

;(defn write-riddle []
;  (loop [i 0]
;    (when(< i (.length word))
;      (println (java.lang.Boolean/valueOf (.contains hits (.charAt word i))))
;      (recur (+ i 1)))))

(defn- obtem-set-chs [word]
  (into #{} (seq (.toCharArray word))))

(defn start-game []
  (let [word (javax.swing.JOptionPane/showInputDialog "Informe a palavra chave")
        chs (obtem-set-chs word)]
    (loop [mistakes 4]
      (when (> mistakes 0)
        (let [c (javax.swing.JOptionPane/showInputDialog "Informe um caractere")] 
          (if (.contains word c) (success-handler c chs) (err-handler mistakes))
          (recur (if (.contains word c) mistakes (dec mistakes))))))
    (javax.swing.JOptionPane/showMessageDialog nil "Você perdeu")))