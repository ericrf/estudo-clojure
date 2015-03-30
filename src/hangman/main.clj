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


(defn- new-game [word hits mistakes] 
  {:word word :chs (into #{} (seq word)) :hits hits :mistakes mistakes})

(defn- win? [game new-hits]
  (= (count new-hits) (count (get game :chs))))

(defn lose? [game]
  (zero? (get game :mistakes)))

(defn- with-secret-word [game c]
  (let [new-hits (into (get game :hits) c)]
    (show-word-swing (get game :word) new-hits)
    (if (win? game new-hits)
      (show-win-message-swing)
      (new-game (get game :word) new-hits (get game :mistakes)))))

(defn- with-body-part [game]
  (do
    (display-body-swing (get game :mistakes))
    (if (lose? game)
      (show-lose-message-swing)
      (new-game (get game :word) (get game :hits) (dec (get game :mistakes))))))

(defn start-hangmain [word]
  (loop [game (new-game word #{} 3)]
    (let [c (ask-for-character-swing)]
      (if (.contains (get game :word) c)
        (recur (with-secret-word game c))
        (recur (with-body-part game))))))