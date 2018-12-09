(ns advent-of-code-2018.day04
  (:require
    [clojure.string :as str]))


(def input (slurp "inputs/day04"))
(def lines
  (->> (str/split-lines input)
       (sort)))


(defn parse-line [l]
  "line => [:guard id] | [:asleep minute] | [:awake minut]"
  (let [[_ m t] (re-matches #"\[\d{4}-\d\d-\d\d \d\d:(\d\d)\] (.*)" l)]
    (cond
      (= t "falls asleep") [:asleep (Integer/parseInt m)]
      (= t "wakes up")     [:awake  (Integer/parseInt m)]
      :else (let [id (re-find #"\d+" t)]
              [:guard (Integer/parseInt id)]))))


(def ^{:doc "[[guard-id asleep awake] ...]"}
  intervals
  (loop [res []
         lines lines
         guard nil
         asleep nil]
    (if (empty? lines)
      res
      (let [[op arg] (parse-line (first lines))]
        (case op
          :guard  (recur res (next lines) arg nil)
          :asleep (recur res (next lines) guard arg)
          :awake (recur (conj res [guard asleep arg]) (next lines) guard nil))))))


(defn guards-asleep-total
  "_ => [guard-id how-often]"
  []
  (reduce
    (fn [m [guard asleep awake]]
      (update m guard #(+ (or % 0) (- awake asleep))))
    {} intervals))


(defn interval->map
  "[_ 5 8] => {5 1, 6 1, 7 1}"
  [[_ asleep awake]]
  (into {}
    (for [m (range asleep awake)]
      [m 1])))


(defn minute-asleep-most
  "guard => [which-minute how-often]"
  [guard]
  (->> intervals
       (filter #(= guard (first %)))
       (map interval->map)
       (apply merge-with +)
       (sort-by second)
       (reverse)
       first))


(defn part1 []
  (let [most-asleep (->> (guards-asleep-total)
                         (sort-by second)
                         (last)
                         (first)) ; 1439
        minute      (->> (minute-asleep-most most-asleep)
                         (first))] ; 42
    (* most-asleep minute)))


;; Part Two


(def guards (into #{} (map first intervals)))


(defn part2 []
  (let [[guard which-minute _] (->> guards
                                    (reduce
                                      (fn [acc guard]
                                        (conj acc (into [guard] (minute-asleep-most guard))))
                                      [])
                                    (sort-by #(nth % 2))
                                    (reverse)
                                    (first))]
    (* guard which-minute)))


(defn -main []
  (println "part1:" (part1))
  (println "part2:" (part2)))
