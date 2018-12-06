(def input-lines
  (clojure.string/split-lines input))


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


(->> input-lines
     (map parse-data)
     (mapcat grid-coords)
     (frequencies)
     (vals)
     (filter #(> % 1))
     (count))
