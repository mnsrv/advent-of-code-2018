(defn parse-input [input]
  (->> (clojure.string/split-lines input)
       (map #(Integer/parseInt %))))


(->> (parse-input input)
     (reduce +))


;; Part Two

(->> (parse-input input)
     cycle
     (reductions +)
     (reduce
        #(if (contains? %1 %2)
          (reduced %2)
          (conj %1 %2))
        #{}))
