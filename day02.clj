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
