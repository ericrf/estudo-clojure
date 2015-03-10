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

(defn- show-input-dialog [sentence]
  (javax.swing.JOptionPane/showInputDialog sentence))

(defn- show-message-dialog [sentence]
  (javax.swing.JOptionPane/showMessageDialog nil sentence))

(defn- display-body-swing [mistakes]
  (show-message-dialog (get [legs arms body head] mistakes)))

(defn- write-word [word hits]
  (map #(if(contains? hits %) % "_") (seq word)))

(defn- show-word-swing [word hits]
   (show-message-dialog (apply str (write-word word hits))))

(defn- request-word-swing []
  (show-input-dialog "Informe a palavra chave!"))

(defn- ask-for-character-swing []
  (show-input-dialog "Informe um caractere."))

(defn- show-win-message-swing []
  (show-message-dialog "Você GANHOU!"))

(defn- show-lose-message-swing []
  (show-message-dialog "Você PERDEU!"))

(defn start-game []
  (let [word (request-word-swing)
        chs (into #{} (seq word))]
    (loop [mistakes 3
           hits #{}]
        (let [c (ask-for-character-swing)] 
          (if (.contains word c)
            (let [new-hits (into hits c)]
              (show-word-swing word new-hits)
              (if (= (count new-hits) (count chs))
                (show-win-message-swing)
                (recur mistakes 
                       new-hits)))
            (do
              (display-body-swing mistakes)
              (if (zero? mistakes)
                (show-lose-message-swing)
                (recur (dec mistakes) hits))))))))