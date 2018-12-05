(defn getFrequencies [input]
  (->> input
       clojure.string/split-lines
       (map frequencies)
       (map vals)
       (map set)
       (mapcat seq)
       frequencies))


(->> (select-keys (getFrequencies input) [2 3])
     vals
     (apply *))


;; Part Two


(def inputVector
  (clojure.string/split-lines input))


(defn char-diff [s1 s2]
  (get (frequencies (map (fn [c1 c2] (= c1 c2)) s1 s2))
       false
       0))


(defn concat-equal-chars [vector]
  (->>  vector
        (apply map #(if (= %1 %2) (str %1) ""))
        (apply str)))


(defn find-pair [input]
  (reduce (fn [acc s]
            (let [diffs (map (fn [s1] { :diff (char-diff s1 s)
                                        :id   s1 })
                              acc)]
              (if (contains? (set (map :diff diffs)) 1)
                (reduced (concat-equal-chars [s (:id (first (filter #(= (:diff %) 1) diffs)))]))
                (rest acc))))
          (rest input)
          input))


(find-pair inputVector)
