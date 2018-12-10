(ns advent-of-code-2018.day05
  (:require
    [clojure.string :as str]))


(def input (slurp "inputs/day05"))


(defn remove-last [str]
  (if (char? str)
    ""
    (str/join "" (drop-last str))))


(defn get-last [str]
  (cond
    (char?      str) str
    (str/blank? str) ""
    :else (last str)))


(defn triggered? [ch1 ch2]
  (cond
    (string? ch1)               false
    (Character/isLowerCase ch1) (= ch2 (last (str/upper-case ch1)))
    :else (= ch2 (last (str/lower-case ch1)))))


(defn part1 []
  (->> input
       (reduce
         (fn [s ch2]
           (if (triggered? (get-last s) ch2)
             (remove-last s)
             (str s ch2))))
       count))


(defn -main []
  (println "part1:" (part1)))