(ns advent-of-code-2018.day05
  (:require
    [clojure.string :as str]))


(def input (slurp "inputs/day05"))


(defn remove-last [str]
  (if (char? str)
    ""
    (subs str 0 (- (count str) 1))))


(defn get-last [str]
  (cond
    (char?      str) str
    (str/blank? str) ""
    :else (last str)))


(defn invert [ch]
  (if (Character/isLowerCase ch)
    (Character/toUpperCase ch)
    (Character/toLowerCase ch)))


(defn triggered? [ch1 ch2]
  (cond
    (string? ch1) false
    :else         (= ch2 (invert ch1))))


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