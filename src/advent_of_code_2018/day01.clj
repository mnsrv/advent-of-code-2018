(ns advent-of-code-2018.day01
  (:require
    [clojure.string :as str]))


(def input (slurp "inputs/day01"))


(def nums
  (->> (str/split-lines input)
       (map #(Integer/parseInt %))))


(defn part1 []
  (reduce + 0 nums))


;; Part Two


(defn part2 []
  (->> nums
       cycle
       (reductions +)
       (reduce
         #(if (contains? %1 %2)
              (reduced %2)
              (conj %1 %2))
         #{})))


(defn -main []
  (println "part1:" (part1))
  (println "part2:" (part2)))
