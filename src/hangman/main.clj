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

(defn- err-handler [mistakes]
  (case mistakes
    4 (javax.swing.JOptionPane/showMessageDialog nil head)
    3 (javax.swing.JOptionPane/showMessageDialog nil body)
    2 (javax.swing.JOptionPane/showMessageDialog nil arms)
    1 (javax.swing.JOptionPane/showMessageDialog nil legs)))

(defn- end-game []
  (javax.swing.JOptionPane/showMessageDialog nil "Você GANHOU!")
  (System/exit 0))

(defn- success-handler [c chs hits]
  (javax.swing.JOptionPane/showMessageDialog nil "Você acertou!")
  (if (= (count (into hits c)) (count chs)) (end-game)))

(defn- obtem-set-chs [word]
  (into #{} (seq (.toCharArray word))))

(defn start-game []
  (let [word (javax.swing.JOptionPane/showInputDialog "Informe a palavra chave")
        chs (obtem-set-chs word)]
    (loop [mistakes 4 
           hits #{}]
      (when (> mistakes 0)
        (let [c (javax.swing.JOptionPane/showInputDialog "Informe um caractere")] 
          (if (.contains word c) (success-handler c chs hits) (err-handler mistakes))
          (recur (if (.contains word c) mistakes (dec mistakes))
                 (into hits c)))))
    (javax.swing.JOptionPane/showMessageDialog nil "Você perdeu")))