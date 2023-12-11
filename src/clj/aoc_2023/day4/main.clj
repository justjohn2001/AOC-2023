(ns aoc-2023.day4.main
  (:require [clojure.java.io :as io]
            [clojure.math :as math]
            [clojure.set :as set]))

(defn line-processor [l]
  (let [[_ winners-str nums-str] (re-matches #"^.*:(.*) \| (.*)$" l)
        winners (read-string (str "#{" winners-str "}"))
        nums (read-string (str "#{" nums-str "}"))]
    (count (set/intersection winners nums))))

(defn part1 [file-name]
  (let [f (io/reader file-name)]
    (transduce (comp (map line-processor)
                     (filter pos?)
                     (map dec)
                     (map (partial math/pow 2))
                     (map int))
               +
               0
               (line-seq f))))

(defn part2 [file-name]
  (let [f (io/reader file-name)
        lines (map-indexed vector
                           (map line-processor
                                (line-seq f)))
        cards (reduce (fn [m [line c]]
                        (let [this (inc (get m line 0))]
                          (transduce (map inc)
                                     (completing
                                      (fn [m' i]
                                        (update m' (+ i line)
                                                (fnil + 0) this)))
                                     (update m line (fnil inc 0))
                                     (range c))
                          ))
                      (sorted-map)
                      lines)]
    (transduce (map second)
               +
               0
               cards)))
