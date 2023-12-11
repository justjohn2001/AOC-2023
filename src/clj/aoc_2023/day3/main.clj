(ns aoc-2023.day3.main
  (:require [clojure.java.io :as io]))

(defn is-digit? [c]
  (get #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9} c))

(defn is-symbol? [c]
  (get #{\# \$ \% \& \* \+ \- \/ \= \@} c))

(defn is-next-to-symbol?
  [l-1 l0 l1 i]
  (let [v (for [l [l-1 l0 l1]
                c [-1 0 1]]
            (and (<= 0 (+ i c) (dec (count l)))
                 (is-symbol? (nth l (+ i c)))))]
    (some identity v))
  )


(defn part1 [file-name]
  (let [f (io/reader file-name)
        m (mapv seq (line-seq f))]
    (loop [l0 nil
           l1 (first m)
           l2 (second m)
           more-lines (drop 2 m)
           parts []]
      (if (seq l1)
        (let [new-parts
              (loop [[c & more-c] l1
                     i 0
                     n 0
                     is-close? false
                     parts' parts]
                (cond
                  (>= i (count l1))
                  (if (and (pos? n) is-close?)
                    (conj parts' n)
                    parts')

                  (is-digit? (nth l1 i))
                  (recur more-c
                         (inc i)
                         (+ (* n 10) (- (int c) 48))
                         (or is-close? (is-next-to-symbol? l0 l1 l2 i))
                         parts')

                  :else
                  (recur more-c
                         (inc i)
                         0
                         false
                         (if (and (pos? n) is-close?)
                           (conj parts' n)
                           parts'))
                  ))]
          (recur l1 l2 (first more-lines) (rest more-lines) new-parts))
        (apply + parts)))))

(defn find-number
  [l i]
  (if (is-digit? (nth l i))
    (let [start (loop [j i]
                  (cond
                    (zero? j) j
                    (is-digit? (nth l (dec j))) (recur (dec j))
                    :else j))]
      (loop [[c & more-c] (drop start l)
             n 0]
        (if (is-digit? c)
          (recur more-c (+ (* n 10) (- (int c) 48)))
          n)))
    0))

(defn neighbor-numbers
  [l-1 l0 l1 i]
  (let [result
        (filter pos?
                (apply concat
                       (for [l [l-1 l0 l1]]
                         (if (is-digit? (nth l i))
                           [(find-number l i)]
                           (concat [(find-number l (dec i))]
                                   [(find-number l (inc i))]
                                   )))))]
    result
    ))

(defn part2 [file-name]
  (let [f (io/reader file-name)
        m (mapv seq (line-seq f))]
    (loop [l0 nil
           l1 (first m)
           l2 (second m)
           more-lines (drop 2 m)
           ratios []]
      (if (seq l1)
        (let [new-ratios
              (loop [[c & more-c] l1
                     i 0
                     ratios' ratios]
                (= c \*)
                (cond
                  (nil? c) ratios'

                  (not= c \*) (recur more-c (inc i) ratios')

                  :else
                  (let [neighbors (neighbor-numbers l0 l1 l2 i)]
                    (if-not (= 2 (count neighbors))
                      (recur more-c (inc i) ratios')
                      (recur more-c (inc i) (conj ratios' (apply * neighbors))))))
                )]
          (recur l1 l2 (first more-lines) (rest more-lines) new-ratios))
        [ratios (apply + ratios)])
      )))
