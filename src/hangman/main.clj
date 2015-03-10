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

(defn- write-body-by [mistakes]
  (get [legs arms body head] mistakes))

(defn- display-body-swing [mistakes]
  (show-message-dialog (write-body-by mistakes)))

(defn- write-word [word hits]
  (apply str (map #(if(contains? hits %) % "_") (seq word))))

(defn- display-word-swing [word hits]
   (show-message-dialog (write-word word hits)))

(defn- display-request-word-swing []
  (show-input-dialog "Informe a palavra chave!"))

(defn- display-ask-for-character-swing []
  (show-input-dialog "Informe um caractere."))

(defn- display-win-message-swing []
  (show-message-dialog "Você GANHOU!"))

(defn- display-lose-message-swing []
  (show-message-dialog "Você PERDEU!"))

(defn- display-request-word [gui-type]
  (case gui-type
    "swing" (display-request-word-swing)))

(defn- display-ask-for-character [gui-type]
  (case gui-type
    "swing" (display-ask-for-character-swing)))

(defn- display-word [gui-type word new-hits]
  (case gui-type
    "swing" (display-word-swing word new-hits)))

(defn- display-win-message [gui-type]
  (case gui-type
    "swing" (display-win-message-swing)))

(defn- display-body [gui-type mistakes]
  (case gui-type
    "swing" (display-body-swing mistakes)))

(defn- display-lose-message [gui-type]
  (case gui-type
    "swing" (display-lose-message-swing)))

(defn start-game [gui-type]
  (let [word (display-request-word gui-type)
        chs (into #{} (seq word))]
    (loop [mistakes 3
           hits #{}]
        (let [c (display-ask-for-character gui-type)] 
          (if (.contains word c)
            (let [new-hits (into hits c)]
              (display-word gui-type word new-hits)
              (if (= (count new-hits) (count chs))
                (display-win-message gui-type)
                (recur mistakes 
                       new-hits)))
            (do
              (display-body gui-type mistakes)
              (if (zero? mistakes)
                (display-lose-message gui-type)
                (recur (dec mistakes) hits))))))))