(ns advent-of-code-2018.day03
  (:require
    [clojure.string :as str]))


(def input (slurp "inputs/day03"))


(defn parse-int [s] (Integer/parseInt s))


(defn parse-data [data]
  (let [[_ id l t w h] (re-find #"^#(\d+) @ (\d+),(\d+): (\d+)x(\d+)$" data)]
    { :id id
      :x0 (parse-int l)
      :y0 (parse-int t)
      :x1 (+ (parse-int l) (parse-int w))
      :y1 (+ (parse-int t) (parse-int h)) }))


(defn grid-coords [{ :keys [x0 y0 x1 y1] }]
  (for [x (range x0 x1)
        y (range y0 y1)]
    [x y]))


(defn part1 []
  (->> (str/split-lines input)
       (map parse-data)
       (mapcat grid-coords)
       (frequencies)
       (vals)
       (filter #(> % 1))
       (count)))


;; Part Two


(defn part2 []
  (let [claims  (map parse-data (str/split-lines input))
        fq      (frequencies (mapcat grid-coords claims))]
    (->> (filter (fn [claim]
                   (every? (fn [coord] (= (get fq coord 0) 1))
                     (grid-coords claim)))
                 claims)
         (first)
         (:id))))


(defn -main []
  (println "part1:" (part1))
  (println "part2:" (part2)))
